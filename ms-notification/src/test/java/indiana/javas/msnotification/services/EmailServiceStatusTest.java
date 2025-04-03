package indiana.javas.msnotification.services;

import indiana.javas.msnotification.entities.EmailModel;
import indiana.javas.msnotification.enums.StatusEmail;
import indiana.javas.msnotification.repositories.EmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static indiana.javas.msnotification.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
public class EmailServiceStatusTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private JavaMailSender mailSender;

    @Test
    public void sendEmail_WhitInvalidEmail_ErrorStatus() {
        Mockito.doThrow(new MailException("Simulated MailException") {})
                .when(mailSender).send(Mockito.any(SimpleMailMessage.class));

        Mockito.when(emailRepository.save(Mockito.any(EmailModel.class)))
                .thenAnswer(invocation -> {
                    INVALID_EMAIL.setStatus(StatusEmail.ERROR);
                    return INVALID_EMAIL;
                });

        EmailModel result = emailService.sendEmail(INVALID_EMAIL);
        Assertions.assertEquals(StatusEmail.ERROR, result.getStatus());

    }

}
