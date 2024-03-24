package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerformanceDTO(
        @NotBlank(message = "title can not be blank")
        String title,
        @NotBlank(message = "description can not be blank")
        String description,
        @NotNull(message = "price can not be null")
        @Min(value = 10, message = "price must be at least 10€")
        double price,
        @NotBlank(message = "location can not be blank")
        String location,
        @NotBlank(message = "image can not be blank")
        String image,
        @NotBlank(message = "category can not be blank")
        String category
) {
}
