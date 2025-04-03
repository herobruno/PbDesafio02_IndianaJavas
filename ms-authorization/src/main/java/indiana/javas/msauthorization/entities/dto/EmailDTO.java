package indiana.javas.msauthorization.entities.dto;

public record EmailDTO(
        Long userId,
        String emailTo,
        String subject,
        String text
) {
}
