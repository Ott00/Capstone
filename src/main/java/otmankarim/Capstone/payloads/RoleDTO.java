package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.NotBlank;

public record RoleDTO(
        @NotBlank(message = "role can not be blank")
        String role
) {
}
