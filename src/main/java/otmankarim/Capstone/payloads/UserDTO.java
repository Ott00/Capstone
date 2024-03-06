package otmankarim.Capstone.payloads;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserDTO(
        @NotBlank(message = "name can not be blank")
        String name,
        @NotBlank(message = "surname can not be blank")
        String surname,
        @NotBlank(message = "email can not be blank")
        @Email
        String email,
        @NotBlank(message = "password can not be blank")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotBlank(message = "phone can not be blank")
        @Pattern(regexp = "\\d{10}", message = "Phone number must contain 10 digits")
        String phone,
        @NotNull
        @Past(message = "the date must be in the past")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday,
        @NotBlank(message = "role can not be blank")
        String role
) {
}
