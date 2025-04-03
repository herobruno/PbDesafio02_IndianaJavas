package indiana.javas.msnotification.services;

import indiana.javas.msnotification.enums.StatusEmail;
import indiana.javas.msnotification.entities.EmailModel;
import indiana.javas.msnotification.repositories.EmailRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    private String fromName = "Indiana Javas";

    @Transactional
    public EmailModel sendEmail(@Valid EmailModel emailModel) {
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setFromEmail(fromEmail);
            emailModel.setReplyTo(fromEmail);
            emailModel.setFromName(fromName);
            emailModel.setContentType("text/html");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailModel.setStatus(StatusEmail.SENT);
            emailSender.send(message);

            return emailRepository.save(emailModel);
        } catch (MailException e) {
            emailModel.setStatus(StatusEmail.ERROR);
            return emailRepository.save(emailModel);

        }

    }
}
