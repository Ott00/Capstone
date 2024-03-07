package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentUpdateDTO(
        @NotBlank(message = "date can not be blank")
        @Future(message = "date must be a future date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotBlank(message = "time can not be blank")
        @Future(message = "time must be a future time")
        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime time
) {
}
