package indiana.javas.msproducts.controller;

import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.dto.mapper.CategoryMapper;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.entities.Product;
import indiana.javas.msproducts.repositories.CategoryRepository;
import indiana.javas.msproducts.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsIT {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category = new Category();
        category.setName("Categoria A");
        categoryRepository.save(category);

        Product product = new Product();
        product.setName("Produto1");
        product.setDescription("Descrição");
        product.setPrice(new BigDecimal("9.99"));
        product.setImgUrl("http://exemplo.com/imagem.jpg");
        product.setDate(LocalDateTime.parse("2023-12-27T10:30:00"));
        productRepository.save(product);
    }


    @Test
    public void getProductById_ExistingProduct_ReturnStatus200() {
        Product product = productRepository.findAll().get(0);

        testClient
                .get()
                .uri("/api/v1/products/" + product.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(product.getId())
                .jsonPath("$.name").isEqualTo(product.getName())
                .jsonPath("$.price").isEqualTo(product.getPrice().doubleValue());
    }

    @Test
    public void deleteProduct_ExistingProduct_ReturnStatus204() {
        Product product = productRepository.findAll().get(0);

        testClient
                .delete()
                .uri("/api/v1/products/" + product.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void listProducts_ReturnStatus200() {
        testClient
                .get()
                .uri("/api/v1/products?page=0&linesPerPage=5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isNotEmpty();
    }

}
