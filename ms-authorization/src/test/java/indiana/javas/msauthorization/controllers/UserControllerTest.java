package indiana.javas.msauthorization.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.entities.dto.UserCreateDTO;
import indiana.javas.msauthorization.entities.dto.UserUpdateDTO;
import indiana.javas.msauthorization.enums.ERole;
import indiana.javas.msauthorization.repositories.UserRepository;
import indiana.javas.msauthorization.services.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static indiana.javas.msauthorization.common.Constants.ADMIN_USER;
import static indiana.javas.msauthorization.common.Constants.VALID_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtTokenService tokenService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserRepository userRepository;

    private User adminUser;
    private User validUser;

    @BeforeEach
    public void setUp() {
        adminUser = new User();
        BeanUtils.copyProperties(ADMIN_USER, adminUser);
        validUser = new User();
        BeanUtils.copyProperties(VALID_USER, validUser);
        validUser.setRoles(Set.of(new Role(ERole.ROLE_OPERATOR)));

        when(userRepository.findByEmail(adminUser.getEmail())).thenReturn(Optional.of(adminUser));
        when(tokenService.generateToken(any())).thenReturn("valid-token");
        when(tokenService.validateToken(any())).thenReturn(adminUser.getEmail());
    }

    @Test
    public void createUser_WithValidData_ReturnsCreated() throws Exception {
        // Arrange
        var newUser = new User();
        BeanUtils.copyProperties(VALID_USER, newUser);

        var userDto = new UserCreateDTO(
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                newUser.getPassword(),
                Set.of(ERole.ROLE_ADMIN)
        );

        when(userRepository.save(any())).thenReturn(newUser);


        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void createUser_WithInvalidData_ReturnsBadRequest() throws Exception {
        var userDto = new UserCreateDTO(
                null,
                null,
                null,
                null,
                null
        );

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUser_WithValidData_ReturnsUser() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        mockMvc.perform(get("/users/{id}", 1L)
                        .header("Authorization", "Bearer valid-token")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(validUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(validUser.getLastName()))
                .andExpect(jsonPath("$.email").value(validUser.getEmail()))
                .andExpect(jsonPath("$.roles").isNotEmpty());
    }

    @Test
    public void getUser_WithInvalidData_ReturnsNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/{id}", 1L)
                .header("Authorization", "Bearer valid-token")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void getUser_WithInvalidAuth_ReturnsForbidden() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/users/{id}", 1L)
        ).andExpect(status().isForbidden());
    }

    @Test
    public void updateUser_WithValidData_ReturnsUpdated() throws Exception {

        var newUserDto = new UserCreateDTO(
                validUser.getFirstName(),
                "Test",
                validUser.getEmail(),
                validUser.getPassword(),
                validUser.getRoles().stream().map(Role::getRole).collect(Collectors.toSet())
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        mockMvc.perform(put("/users/{id}", 1L)
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_WithInvalidData_ReturnsNoContent() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        mockMvc.perform(put("/users/{id}", 1L)
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserUpdateDTO(null, null, null, null, null)))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_WithInvalidAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserUpdateDTO(null, null, null, null, null)))
                )
                .andExpect(status().isForbidden());
    }
}
