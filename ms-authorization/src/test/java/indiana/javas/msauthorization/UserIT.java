package indiana.javas.msauthorization;


import indiana.javas.msauthorization.common.Authentication;
import indiana.javas.msauthorization.entities.Role;
import indiana.javas.msauthorization.entities.dto.UserCreateDTO;
import indiana.javas.msauthorization.entities.dto.UserResponseDTO;
import indiana.javas.msauthorization.entities.dto.UserUpdateDTO;
import indiana.javas.msauthorization.enums.ERole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;
import java.util.stream.Collectors;

import static indiana.javas.msauthorization.common.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/insert_roles.sql", "/sql/insert_users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient webClient;

    @Test
    public void createUser_WithValiData_ReturnsCreate() {
        var user = new UserCreateDTO(
                VALID_USER.getFirstName(),
                VALID_USER.getLastName(),
                VALID_USER.getEmail(),
                VALID_USER.getPassword(),
                Set.of(ERole.ROLE_OPERATOR)
        );

        webClient.post()
                .uri("/users")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void createUser_WithInvalidData_ReturnsBadRequest() {
        webClient.post()
                .uri("/users")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO(null, null, null, null, null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getUser_ValidUser_ReturnsUser() {
        var expectedUser = new UserResponseDTO(
                JORGE_BAGRE.getFirstName(),
                JORGE_BAGRE.getLastName(),
                JORGE_BAGRE.getEmail(),
                JORGE_BAGRE.getRoles().stream().map(Role::getRole).collect(Collectors.toSet())
        );

        webClient.get()
                .uri("/users/2")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo(expectedUser);
    }

    @Test
    public void getUser_InvalidUser_ReturnsNotFound() {
        webClient.get()
                .uri("/users/0")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    public void getUser_WihInvalidAuth_ReturnsForbidden() {
        webClient.get()
                .uri("/users/2")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void updateUser_ValidUser_UpdatesUserReturnsNoContent() {
        var newUser = new UserUpdateDTO(
                JORGE_BAGRE.getFirstName(),
                "Lambari",
                JORGE_BAGRE.getEmail(),
                JORGE_BAGRE.getPassword(),
                JORGE_BAGRE.getRoles().stream().map(Role::getRole).collect(Collectors.toSet())
        );

        webClient.put()
                .uri("/users/2")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody();
    }

    @Test
    public void updateUser_InvalidUser_ReturnsNoContent() {
        webClient.put()
                .uri("/users/2")
                .headers(Authentication.getHeaderAuth(webClient, ADMIN_USER.getEmail(), ADMIN_USER.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdateDTO(null, null, null, null, null))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updateUser_InvalidAuth_ReturnsForbidden() {
        webClient.put()
                .uri("/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserUpdateDTO(null, null, null, null, null))
                .exchange()
                .expectStatus().isForbidden();
    }
}
