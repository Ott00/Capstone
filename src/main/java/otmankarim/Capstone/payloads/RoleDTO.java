package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.NotBlank;

public record RoleDTO(
        @NotBlank
        String role
) {
}
