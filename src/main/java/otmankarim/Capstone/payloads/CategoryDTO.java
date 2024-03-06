package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank(message = "category name can not be blank")
        String name
) {
}
