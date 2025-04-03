package indiana.javas.msproducts.common;

import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.dto.ProductResponseDto;
import indiana.javas.msproducts.dto.mapper.ProductMapper;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.entities.Product;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testMapperConstructor() {
        new ProductMapper();
    }

    @Test
    void testToProduct() {
        ProductDto dto = new ProductDto(
                "Produto Teste",
                "Descrição Teste",
                BigDecimal.valueOf(99.99),
                "http://imagem.com/teste.png",
                LocalDateTime.now(),
                Collections.emptySet()
        );

        Product product = ProductMapper.toProduct(dto);
        assertNotNull(product);
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImgUrl(), product.getImgUrl());
        assertEquals(dto.getDate(), product.getDate());
        assertTrue(product.getCategories().isEmpty());
    }

    @Test
    void testToDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");
        product.setDescription("Descrição Teste");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setImgUrl("http://imagem.com/teste.png");
        product.setDate(LocalDateTime.now());
        product.setCategories(Collections.singleton(new Category(1L, "Categoria Teste")));


        ProductResponseDto dto = ProductMapper.toDto(product);
        assertNotNull(dto);
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getImgUrl(), dto.getImgUrl());
        assertEquals(product.getDate(), dto.getDate());
        assertEquals(1, dto.getCategories().size());
    }


    @Test
    void testToListDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");
        product.setDescription("Descrição Teste");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setImgUrl("http://imagem.com/teste.png");
        product.setDate(LocalDateTime.now());
        product.setCategories(Collections.singleton(new Category(1L, "limpeza", new HashSet<>())));

        List<Product> products = Collections.singletonList(product);

        List<ProductResponseDto> dtos = ProductMapper.toListDto(products);


        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(product.getName(), dtos.get(0).getName());
        assertEquals(product.getDescription(), dtos.get(0).getDescription());
    }


    @Test
    void testToListDtoWithEmptyList() {
        List<Product> products = Collections.emptyList();
        List<ProductResponseDto> dtos = ProductMapper.toListDto(products);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
}

