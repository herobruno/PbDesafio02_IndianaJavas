package indiana.javas.msproducts.services;

import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.entities.Product;
import indiana.javas.msproducts.exceptions.ProductNotFoundException;
import indiana.javas.msproducts.exceptions.ResourceNotFoundException;
import indiana.javas.msproducts.repositories.CategoryRepository;
import indiana.javas.msproducts.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("Produto Teste");
        dto.setDescription("Descrição Teste");
        dto.setPrice(BigDecimal.valueOf(100.0));
        dto.setImgUrl("imagem.jpg");
        dto.setDate(null);
        dto.setCategories(Collections.emptySet());

        Product product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(dto, null);
        assertNotNull(createdProduct);
        assertEquals("Produto Teste", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        boolean result = productService.deleteProduct(1L);
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product foundProduct = productService.findById(1L);
        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findById(1L));
    }
    @Test
    void createProduct_ValidDto_ReturnsProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("Test Product");
        dto.setDescription("Test Description");
        dto.setPrice(BigDecimal.valueOf(100.0));
        dto.setImgUrl("http://test.com/img.jpg");
        dto.setCategories(Set.of(1L));
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test Product");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        Product result = productService.createProduct(dto, null);
        assert result.getId() == 1L;
    }

    @Test
    void deleteProduct_ExistingId_DeletesProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        boolean result = productService.deleteProduct(1L);
        assert result;
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProducts_ValidPageRequest_ReturnsPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Product product = new Product();
        product.setId(1L);
        Page<Product> page = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(pageRequest)).thenReturn(page);
        Page<Product> result = productService.getProducts(pageRequest);
        assert result.getTotalElements() == 1;
    }
}
