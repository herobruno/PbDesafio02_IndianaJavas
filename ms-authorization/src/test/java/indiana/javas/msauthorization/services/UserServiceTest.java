package indiana.javas.msauthorization.services;

import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.exceptions.UserNotFoundException;
import indiana.javas.msauthorization.producers.UserProducer;
import indiana.javas.msauthorization.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static indiana.javas.msauthorization.common.Constants.VALID_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
public class UserServiceTest {

    @Autowired
    @InjectMocks
    private UserService userService;

    @MockitoBean
    @Mock
    private UserRepository userRepository;

    @MockitoBean
    @Mock
    private RoleService roleService;

    @MockitoBean
    @Mock
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    @Mock
    private UserProducer userProducer;

    private User validUser;

    @BeforeEach
    public void setup() {
        validUser = new User();
        BeanUtils.copyProperties(VALID_USER, validUser);
    }

    @Test
    public void save_WithValidUserAndRoles_SavesAndReturnsUser() {
        // Arrange
        var enumRoles = Set.of(ERole.ROLE_ADMIN);
        var roles = enumRoles.stream().map(Role::new).toList();

        when(roleService.findAllByRole(enumRoles)).thenReturn(roles);
        when(passwordEncoder.encode(validUser.getPassword())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        // Act
        User savedUser = userService.save(validUser, enumRoles);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(validUser.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo("encoded");
        assertThat(savedUser.getRoles()).isEqualTo(new HashSet<>(roles));

    }

    @Test
    public void findById_WithExistingId_ReturnsUser() {
        // Arrange
        validUser.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(validUser));

        // Act
        User foundUser = userService.findById(1L);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
        assertThat(foundUser.getEmail()).isEqualTo(validUser.getEmail());
        assertThat(foundUser.getPassword()).isEqualTo(validUser.getPassword());
    }

    @Test
    public void findById_WithNonExistingId_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.findById(1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void findByEmail_WithExistingEmail_ReturnsUser() {
        // Arrange
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(validUser));

        // Act
        Optional<User> foundUser = userService.findByEmail(validUser.getEmail());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(validUser.getEmail());
        assertThat(foundUser.get().getPassword()).isEqualTo(validUser.getPassword());
    }

    @Test
    public void findByEmail_WithNonExistingEmail_ReturnsEmpty() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findByEmail("test@example.com");

        // Assert
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void update_WithValidData_UpdatesUser() {
        // Arrange
        validUser.setId(1L);

        User newUser = new User();
        newUser.setId(validUser.getId());
        newUser.setFirstName("Jorge");
        newUser.setLastName("Bagre");
        newUser.setEmail("jorge@bagre.com");

        var enumRoles = Set.of(ERole.ROLE_OPERATOR, ERole.ROLE_ADMIN);
        var roles = enumRoles.stream().map(Role::new).toList();

        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        when(roleService.findAllByRole(enumRoles)).thenReturn(roles);

        // Act
        userService.update(1L, newUser, enumRoles);

        // Assert
        assertThat(validUser.getFirstName()).isEqualTo(newUser.getFirstName());
        assertThat(validUser.getLastName()).isEqualTo(newUser.getLastName());
        assertThat(validUser.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(validUser.getRoles()).isEqualTo(new HashSet<>(roles));

        verify(userProducer).publishMessage(argThat(user -> Objects.equals(user.getId(), newUser.getId())));
    }

}
