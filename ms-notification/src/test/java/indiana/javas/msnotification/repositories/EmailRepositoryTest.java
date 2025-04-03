package indiana.javas.msnotification.repositories;

import indiana.javas.msnotification.entities.EmailModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static indiana.javas.msnotification.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class EmailRepositoryTest {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createEmail_WithValidData_ReturnsEmail() {
        EmailModel emailModel = emailRepository.save(VALID_EMAIL);

        EmailModel sut = testEntityManager.find(EmailModel.class, emailModel.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getEmailTo()).isEqualTo(VALID_EMAIL.getEmailTo());
        assertThat(sut.getSubject()).isEqualTo(VALID_EMAIL.getSubject());
        assertThat(sut.getText()).isEqualTo(VALID_EMAIL.getText());
    }

    @Test
    public void createEmail_WithInvalidData_ThrowsException() {

       assertThatThrownBy(() -> emailRepository.save(INVALID_EMAIL));
       assertThatThrownBy(() -> emailRepository.save(INVALID_SUBJECT));
       assertThatThrownBy(() -> emailRepository.save(INVALID_TEXT));
    }
}
