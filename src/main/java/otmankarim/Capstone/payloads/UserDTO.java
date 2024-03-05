package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserDTO(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotBlank
        @Pattern(regexp = "\\d{10}", message = "Phone number must contain 10 digits")
        String phone,
        @NotNull
        @Past(message = "The date must be in the past")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday,
        @NotBlank
        String role
) {
}
