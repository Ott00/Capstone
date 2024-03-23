package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.AppointmentStatus;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.AppointmentStatusDTO;
import otmankarim.Capstone.services.AppointmentStatusSRV;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointmentStatus")
public class AppointmentStatusCTRL {

    @Autowired
    private AppointmentStatusSRV appointmentStatusSRV;

    @GetMapping
    public List<AppointmentStatus> getAppointmentStatus() {
        return this.appointmentStatusSRV.getAppointmentStatus();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentStatus saveAppointmentStatus(@RequestBody @Validated AppointmentStatusDTO newAppointmentStatus, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.appointmentStatusSRV.save(newAppointmentStatus);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointmentStatus(@PathVariable UUID id) {
        this.appointmentStatusSRV.delete(id);
    }
}
