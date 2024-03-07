package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewDTO(
        @NotNull(message = "evaluation can not be null")
        @Min(value = 1)
        @Max(value = 5)
        int evaluation,
        @NotBlank(message = "title can not be blank")
        String title,
        @NotBlank(message = "comment can not be blank")
        String comment,
        @NotNull(message = "performance_id can not be null")
        UUID performance_id
) {
}
