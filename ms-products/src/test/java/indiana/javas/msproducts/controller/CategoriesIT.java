package indiana.javas.msproducts.controller;

import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.dto.CategoryReponseDto;
import indiana.javas.msproducts.exceptions.RestErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/categories/categories-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/categories/categories-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoriesIT {

    @Autowired
    WebTestClient testClient;

 /*   @Test
    public void createCategory_withValidName_ReturnCreatedCategoryWithStatus201() {
        CategoryReponseDto responseBody = testClient
                .post()
                .uri("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDto("sports"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryReponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();

    }
*/

   /* @Test
    public void createCategory_withValidName_ReturnCreatedCategoryWithStatus201() {
        testClient
                .post()
                .uri("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDto(""))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isNotEmpty();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
       org.assertj.core.api.Assertions.assertThat(responseBody).getId()).isNotNull();
    } */

    /*  @Test
    public void createCategory_withInvalidName_ReturnErrorMessageStatus422() {
        RestErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CategoryDto(""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(RestErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }*/

    @Test
    public void getCategory_withValidId_ReturnCategoryDtoAndStatus200() {
        CategoryReponseDto responseBody = testClient
                .get()
                .uri("/api/v1/categories/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryReponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    }

}
