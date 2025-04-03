package indiana.javas.msauthorization.services;


import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static indiana.javas.msauthorization.common.Constants.VALID_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthorizationService.class})
public class AuthorizationServiceTest {

    @Autowired
    @InjectMocks
    private AuthorizationService authorizationService;

    @MockitoBean
    @Mock
    private UserRepository userRepository;

    @Test
    public void loadUser_WithValidUsername_ReturnsUser() {
        var user = new User();
        BeanUtils.copyProperties(VALID_USER, user );
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails savedUser = authorizationService.loadUserByUsername(user.getEmail());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void loadUser_WithInvalidUsername_ThrowsException() {
        assertThatThrownBy(() -> authorizationService.loadUserByUsername("invalid")).isInstanceOf(UsernameNotFoundException.class);
    }
}
