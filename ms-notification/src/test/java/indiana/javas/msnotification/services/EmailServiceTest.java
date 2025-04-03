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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static indiana.javas.msnotification.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailRepository emailRepository;



    @Test
    public void sendEmail_WhitInvalidData_ThrowsException() {

        assertThatThrownBy(() -> emailService.sendEmail(INVALID_EMAIL)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> emailService.sendEmail(INVALID_SUBJECT)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> emailService.sendEmail(INVALID_TEXT)).isInstanceOf(RuntimeException.class);
    }


}
