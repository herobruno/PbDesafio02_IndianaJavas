package indiana.javas.msauthorization.entities.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @Email(message = "Must be a valid email")
        @Size(max = 150, message = "Email must have a maximum of 150 characters")
        @NotBlank(message = "Email must not be blank")
        String email,

        @Size(min = 8, max = 8, message = "Password must have exactly 8 characters")
        @NotBlank(message = "Password must not be blank")
        String password
) {
}
