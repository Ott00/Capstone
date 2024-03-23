package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.NotBlank;

public record AppointmentStatusDTO(
        @NotBlank(message = "appointment_status name can not be blank")
        String status
) {
}
