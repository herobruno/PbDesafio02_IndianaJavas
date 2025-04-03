package indiana.javas.msauthorization.common;

import indiana.javas.msauthorization.entities.dto.LoginResponseDTO;
import indiana.javas.msauthorization.entities.dto.UserLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class Authentication {
    public static Consumer<HttpHeaders> getHeaderAuth(WebTestClient webClient, String email, String password) {
        String token = webClient
                .post()
                .uri("/oauth/token")
                .bodyValue(new UserLoginDTO(email, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginResponseDTO.class)
                .returnResult().getResponseBody().token();

        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
