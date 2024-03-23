package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAppointmentStatusDTO(
        @NotNull(message = "appointment_status_id can not be null")
        UUID appointment_status_id
) {
}
