package indiana.javas.msauthorization.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.entities.dto.UserLoginDTO;
import indiana.javas.msauthorization.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static indiana.javas.msauthorization.common.Constants.ADMIN_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtTokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void token_WithValidCredentials_ReturnsTokenAndUserDetails() throws Exception {
        // Arrange
        var user = new User();
        BeanUtils.copyProperties(ADMIN_USER, user);
        user.setId(1L);

        when(tokenService.generateToken(any())).thenReturn("token");

        UserLoginDTO loginDTO = new UserLoginDTO(user.getEmail(), user.getPassword());

        // Act & Assert
        mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.roles").isNotEmpty());
    }

    @Test
    void token_WithInvalidCredentials_ReturnError() throws Exception {
        // Arrange
        UserLoginDTO loginDTO = new UserLoginDTO("invalid@mail.com", "12345678");

        // Act & Assert
        mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void token_WithInvalidData_ReturnErrorMessage() throws Exception {
        // Arrange
        UserLoginDTO loginDTO = new UserLoginDTO("", "");

        // Act & Assert
        mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO))
                )
                .andExpect(status().isBadRequest());

    }
}
