package indiana.javas.msnotification.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record EmailRecordDto(
        Long id,

        @NotNull(message = "User must not be null")
        Long userId,

        @Email(message = "Must be a valid email")
        @Size(max = 150, message = "Email must have a maximum of 150 characters")
        @NotBlank(message = "Email must not be blank")
        String emailTo,

        @Size(max = 150, message = "Subject must have a maximum of 150 characters")
        @NotBlank(message = "Subject must not be blank")
        String subject,

        @NotBlank(message = "Text must not be blank")
        String text
) {
}
