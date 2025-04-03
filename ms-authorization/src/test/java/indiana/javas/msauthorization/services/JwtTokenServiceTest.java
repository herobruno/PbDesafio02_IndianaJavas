package indiana.javas.msauthorization.services;


import indiana.javas.msauthorization.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static indiana.javas.msauthorization.common.Constants.VALID_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(classes = JwtTokenService.class)
public class JwtTokenServiceTest {

    @InjectMocks
    private JwtTokenService jwtTokenService;

    private static final String SECRET = "testSecret";
    private static final String ISSUER = "testIssuer";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtTokenService, "issuer", ISSUER);
    }

    @Test
    public void generateToken_WithValidUser_ReturnsToken() {
        // Arrange
        var user = new User();
        BeanUtils.copyProperties(VALID_USER, user);

        // Act
        String token = jwtTokenService.generateToken(user);

        // Assert
        assertThat(token).isNotNull();
    }

    @Test
    public void generateToken_WithNullUser_ThrowsException() {
        assertThatThrownBy(() -> jwtTokenService.generateToken(null))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void validateToken_WithValidToken_ReturnsSubject() {
        // Arrange
        var user = new User();
        BeanUtils.copyProperties(VALID_USER, user);
        String token = jwtTokenService.generateToken(user);

        // Act
        String subject = jwtTokenService.validateToken(token);

        // Assert
        assertThat(subject).isEqualTo(user.getEmail());
    }

    @Test
    public void validateToken_WithInvalidToken_ReturnsNull() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act
        String subject = jwtTokenService.validateToken(invalidToken);

        // Assert
        assertThat(subject).isNull();
    }
}