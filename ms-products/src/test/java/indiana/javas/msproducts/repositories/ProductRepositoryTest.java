package indiana.javas.msproducts.repositories;

import indiana.javas.msproducts.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(100.0));
    }

    @Test
    public void testSaveProduct() {
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void testFindProductById() {
        Product savedProduct = productRepository.save(product);
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertNotNull(foundProduct);
        assertEquals(savedProduct.getId(), foundProduct.getId());
    }

    @Test

    public void testDeleteProduct() {
        Product savedProduct = productRepository.save(product);
        productRepository.delete(savedProduct);
        Product deletedProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertNull(deletedProduct);
    }
}
