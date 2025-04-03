package indiana.javas.msproducts.common;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class ProductConstants {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setDescription("Product description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setImgUrl("http://example.com/img.jpg");
        product.setDate(LocalDateTime.now());
    }

    @Test
    public void testProductCreation() {
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Product A", product.getName());
        assertEquals("Product description", product.getDescription());
        assertEquals(BigDecimal.valueOf(100.0), product.getPrice());
        assertEquals("http://example.com/img.jpg", product.getImgUrl());
        assertNotNull(product.getDate());
    }

    @Test
    public void testProductToString() {
        String productString = product.toString();

        assertTrue(productString.contains("id=" + product.getId()));
        assertTrue(productString.contains("name='" + product.getName() + "'"));
        assertTrue(productString.contains("description='" + product.getDescription() + "'"));
        assertTrue(productString.contains("price=" + product.getPrice().toPlainString()));
        assertTrue(productString.contains("imgUrl='" + product.getImgUrl() + "'"));
        assertTrue(productString.contains("date=" + product.getDate().toString()));
        assertTrue(productString.contains("categories=" + product.getCategories().toString()));
    }

    @Test
    public void testAddCategory() {
        Category category = new Category(1L, "limpeza", new HashSet<>());
        product.getCategories().add(category);
        assertTrue(product.getCategories().contains(category));
    }
}
