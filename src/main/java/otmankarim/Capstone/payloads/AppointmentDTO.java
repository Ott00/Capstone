package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentDTO(
        @NotNull(message = "date can not be blank")
        @Future(message = "date must be a future date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotNull(message = "time can not be blank")
        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime time,
        @NotNull(message = "performance_id can not be null")
        UUID performance_id
) {
}
