package indiana.javas.msnotification.consumers;

import indiana.javas.msnotification.dtos.EmailRecordDto;
import indiana.javas.msnotification.repositories.EmailRepository;
import indiana.javas.msnotification.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static indiana.javas.msnotification.common.Constants.VALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailConsumerTest {

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailRecordDto emailRecordDto;

    @Test
    public void emailQueue_WithValidEmailRecordDto_InvokesEmailService() {

    }
}
