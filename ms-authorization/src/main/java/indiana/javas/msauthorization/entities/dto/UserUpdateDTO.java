package indiana.javas.msauthorization.entities.dto;

import indiana.javas.msauthorization.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserUpdateDTO(
        @Size(max = 50, message = "First name must have a maximum of 50 characters")
        String firstName,

        @Size(max = 100, message = "Last name must have a maximum of 100 characters")
        String lastName,

        @Email(message = "Must be a valid email")
        @Size(max = 150, message = "Email must have a maximum of 150 characters")
        String email,

        @Size(min = 8, max = 8, message = "Password must have exactly 8 characters")
        String password,

        Set<ERole> roles
) {
}
