package indiana.javas.msauthorization.entities.dto;

import indiana.javas.msauthorization.enums.ERole;

import java.util.Set;

public record LoginResponseDTO(
        String token,
        String email,
        Set<ERole> roles
) {
}
