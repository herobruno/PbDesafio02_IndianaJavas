package indiana.javas.msauthorization.entities.dto;

import indiana.javas.msauthorization.enums.ERole;

import java.util.Set;

public record UserResponseDTO(
        String firstName,
        String lastName,
        String email,
        Set<ERole> roles
) {
}
