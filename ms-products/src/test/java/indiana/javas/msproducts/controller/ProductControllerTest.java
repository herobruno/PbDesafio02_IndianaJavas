package indiana.javas.msproducts.controller;

import indiana.javas.msproducts.controllers.ProductControllers;
import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.dto.ProductResponseDto;
import indiana.javas.msproducts.entities.Product;
import indiana.javas.msproducts.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProductControllerTest {

    @InjectMocks
    private ProductControllers productControllers;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productControllers).build();
    }


    @Test
    void deleteProduct() {
        when(productService.deleteProduct(1L)).thenReturn(true);
        ResponseEntity<Void> response = productControllers.deleteProduct(1L);
        assertEquals(204, response.getStatusCodeValue(), "Deve retornar código 204 (No Content)");
        when(productService.deleteProduct(2L)).thenReturn(false);
        ResponseEntity<Void> notFoundResponse = productControllers.deleteProduct(2L);
        assertEquals(404, notFoundResponse.getStatusCodeValue(), "Deve retornar código 404 (Not Found) se o produto não existir");
    }

    @Test
    void getProductById() {
        Product product = new Product(1L, "Produto1", "Descrição", new BigDecimal("9.99"), "http://imagem.jpg", LocalDateTime.now(), Collections.emptySet());
        when(productService.findById(1L)).thenReturn(product);
        ResponseEntity<Product> response = productControllers.getProductById(1L);
        assertEquals(200, response.getStatusCodeValue(), "Deve retornar código 200 (OK)");
        assertNotNull(response.getBody(), "O corpo da resposta não pode ser nulo");
        assertEquals(product.getId(), response.getBody().getId(), "O ID do produto retornado deve ser o mesmo do produto criado");
    }


    @Test
    void getProducts() {
        Page<Product> page = new PageImpl<>(Collections.singletonList(new Product(1L, "Produto1", "Descrição", new BigDecimal("9.99"), "http://imagem.jpg", LocalDateTime.now(), Collections.emptySet())));
        when(productService.getProducts(any(PageRequest.class))).thenReturn(page);
        Page<Product> response = productControllers.getProducts(0, 5, "ASC", "name");
        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals(1, response.getContent().size(), "Deve conter 1 produto na página");
    }
}
