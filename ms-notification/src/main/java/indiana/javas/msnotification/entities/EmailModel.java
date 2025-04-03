package indiana.javas.msnotification.entities;

import indiana.javas.msnotification.enums.StatusEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_EMAILS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class EmailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String fromEmail;
    private String fromName;
    private String replyTo;

    @NotEmpty
    @Column(nullable = false)
    @Email
    private String emailTo;

    @NotEmpty
    @Column(nullable = false)
    private String subject;

    @NotEmpty
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;


    private String contentType;
    private LocalDateTime sendDateEmail;

    @Enumerated(EnumType.STRING)
    private StatusEmail status;

    public EmailModel(Long userId, String emailTo, String subject, String text) {
        this.userId = userId;
        this.emailTo = emailTo;
        this.subject = subject;
        this.text = text;
    }
}
