package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Appointment;
import otmankarim.Capstone.entities.AppointmentStatus;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.AppointmentDTO;
import otmankarim.Capstone.payloads.AppointmentUpdateDTO;
import otmankarim.Capstone.payloads.UpdateAppointmentStatusDTO;
import otmankarim.Capstone.repositories.AppointmentDAO;

import java.util.UUID;

@Service
public class AppointmentSRV {
    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private PerformanceSRV performanceSRV;

    @Autowired
    private AppointmentStatusSRV appointmentStatusSRV;

    public Page<Appointment> getAppointments(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return appointmentDAO.findAll(pageable);
    }

    public Page<Appointment> getMyAppointments(int pageNum, int size, String orderBy, User user) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));

        if (user.getRole().getRole().equals("FREELANCER")) {
            return appointmentDAO.appointmentsByFreelancer(user, pageable);
        } else return appointmentDAO.appointmentsByClient(user, pageable);

    }

    public Appointment save(AppointmentDTO newAppointment, User client) {
        if (appointmentDAO.existsAppointmentInSameDateTime(newAppointment.date(), newAppointment.time(), client)) {
            throw new BadRequestException(client.getEmail() + " already have an appointment in same date and time");
        }
        AppointmentStatus appointmentStatus = appointmentStatusSRV.getAppointmentStatusByName("ATTESA");
        Performance performance = performanceSRV.getPerformanceById(newAppointment.performance_id());

        Appointment appointment = new Appointment(
                newAppointment.date(),
                newAppointment.time(),
                appointmentStatus,
                client,
                performance
        );
        return appointmentDAO.save(appointment);
    }

    public Appointment getAppointmentById(UUID id) {
        return appointmentDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Appointment updateAppointmentById(AppointmentUpdateDTO updatedAppointment, UUID id) {
        Appointment found = getAppointmentById(id);
        found.setDate(updatedAppointment.date());
        found.setTime(updatedAppointment.time());
        return appointmentDAO.save(found);
    }

    public Appointment updateAppointmentStatusById(UUID appointmentId, UpdateAppointmentStatusDTO appointmentStatusDTO) {
        Appointment found = getAppointmentById(appointmentId);
        AppointmentStatus appointmentStatus = appointmentStatusSRV.getAppointmentStatusById(appointmentStatusDTO.appointment_status_id());
        found.setAppointmentStatus(appointmentStatus);
        return appointmentDAO.save(found);
    }

    public void delete(UUID id) {
        Appointment found = getAppointmentById(id);
        appointmentDAO.delete(found);
    }
}
