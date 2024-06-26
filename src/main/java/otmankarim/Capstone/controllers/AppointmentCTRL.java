package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.Appointment;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.AppointmentDTO;
import otmankarim.Capstone.payloads.AppointmentUpdateDTO;
import otmankarim.Capstone.payloads.UpdateAppointmentStatusDTO;
import otmankarim.Capstone.services.AppointmentSRV;

import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentCTRL {
    @Autowired
    private AppointmentSRV appointmentSRV;

    @GetMapping
    public Page<Appointment> getAppointments(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String orderBy) {
        return this.appointmentSRV.getAppointments(page, size, orderBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('FREELANCER','CLIENT')")
    public Page<Appointment> getMyPerformances(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "date") String orderBy,
                                               @AuthenticationPrincipal User user) {
        return this.appointmentSRV.getMyAppointments(page, size, orderBy, user);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable UUID id) {
        return this.appointmentSRV.getAppointmentById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment saveAppointment(@AuthenticationPrincipal User client, @RequestBody @Validated AppointmentDTO newAppointment, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.appointmentSRV.save(newAppointment, client);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment updateAppointmentById(@RequestBody @Validated AppointmentUpdateDTO updatedAppointment, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.appointmentSRV.updateAppointmentById(updatedAppointment, id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FREELANCER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Appointment updateAppointmentStatusById(@RequestBody @Validated UpdateAppointmentStatusDTO updateAppointmentStatusDTO, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.appointmentSRV.updateAppointmentStatusById(id, updateAppointmentStatusDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable UUID id) {
        this.appointmentSRV.delete(id);
    }

}
