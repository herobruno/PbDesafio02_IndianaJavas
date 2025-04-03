package indiana.javas.msauthorization.repositories;


import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.Set;

import static indiana.javas.msauthorization.common.Constants.VALID_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@Sql(scripts = "/sql/insert_roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User validUser;

    @BeforeEach
    public void setUp() {
        validUser = new User();
        BeanUtils.copyProperties(VALID_USER, validUser);
    }

    @Test
    public void createUser_WithValidData_ReturnsUser() {
        // Arrange
        var role = entityManager.find(Role.class, 1L);
        validUser.setRoles(Set.of(role));

        // Act
        User savedUser = userRepository.save(validUser);

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo(validUser.getFirstName());
        assertThat(savedUser.getLastName()).isEqualTo(validUser.getLastName());
        assertThat(savedUser.getEmail()).isEqualTo(validUser.getEmail());
        assertThat(savedUser.getRoles()).isEqualTo(Set.of(role));
    }

    @Test
    public void createUser_WithInvalidData_ThrowsException() {
        User user = new User();
        assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void findByEmail_ExistingUser_ReturnsUser() {
        // Arrange
        entityManager.persist(validUser);

        // Act
        Optional<User> foundUser = userRepository.findByEmail(validUser.getEmail());

        // Assert
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getFirstName()).isEqualTo(validUser.getFirstName());
        assertThat(foundUser.get().getLastName()).isEqualTo(validUser.getLastName());
        assertThat(foundUser.get().getEmail()).isEqualTo(validUser.getEmail());
    }

    @Test
    public void findByEmail_NonExistingUser_ReturnsEmpty() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("ines@xistente.com");

        // Assert
        assertThat(foundUser.isEmpty()).isTrue();
    }
}
