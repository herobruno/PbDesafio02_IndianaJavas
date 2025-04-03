package indiana.javas.msnotification.repositories;

import indiana.javas.msnotification.entities.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {

}
