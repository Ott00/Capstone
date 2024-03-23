package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.AppointmentStatus;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.AppointmentStatusDTO;
import otmankarim.Capstone.repositories.AppointmentStatusDAO;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentStatusSRV {

    @Autowired
    private AppointmentStatusDAO appointmentStatusDAO;

    public List<AppointmentStatus> getAppointmentStatus() {
        return appointmentStatusDAO.findAll();
    }

    public AppointmentStatus save(AppointmentStatusDTO newStatus) {
        if (appointmentStatusDAO.existsByStatus(newStatus.status())) {
            throw new BadRequestException(newStatus.status() + " already exist");
        }
        AppointmentStatus appointmentStatus = new AppointmentStatus(
                newStatus.status()
        );
        return appointmentStatusDAO.save(appointmentStatus);
    }

    public AppointmentStatus getAppointmentStatusById(UUID id) {
        return appointmentStatusDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public AppointmentStatus getAppointmentStatusByName(String status) {
        return appointmentStatusDAO.findByStatus(status).orElseThrow(() -> new NotFoundException(status));
    }

    public void delete(UUID id) {
        AppointmentStatus found = getAppointmentStatusById(id);
        appointmentStatusDAO.delete(found);
    }
}
