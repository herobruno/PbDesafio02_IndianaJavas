package indiana.javas.msproducts.common;



import indiana.javas.msproducts.dto.ProductDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateNameIsRequired() {
        ProductDto dto = new ProductDto(
                "",
                "Descrição válida",
                BigDecimal.valueOf(10.0),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldValidateDescriptionMaxLength() {
        String longDescription = "A".repeat(256);

        ProductDto dto = new ProductDto(
                "Produto Válido",
                longDescription,
                BigDecimal.valueOf(10.0),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("A descrição deve ter no máximo 255 caracteres"));
    }
    @Test
    void shouldValidateDescriptionIsRequired() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "",
                BigDecimal.valueOf(10.0),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("A descrição é obrigatória"));
    }
    @Test
    void shouldValidatePriceIsRequired() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "Descrição válida",
                null,
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);


        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("O preço é obrigatório"));
    }

    @Test
    void shouldValidatePriceGreaterThanZero() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "Descrição válida",
                BigDecimal.valueOf(0.00),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);


        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("O preço deve ser maior que zero"));
    }

    @Test
    void shouldValidateValidPrice() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "Descrição válida",
                BigDecimal.valueOf(10.00),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
    @Test
    void shouldValidateImageUrlIsRequired() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "Descrição válida",
                BigDecimal.valueOf(10.0),
                "",
                LocalDateTime.now(),
                null
        );

        var violations = validator.validate(dto);


        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("A URL da imagem é obrigatória"));
    }
    @Test
    void shouldValidateDateIsNotInFuture() {
        ProductDto dto = new ProductDto(
                "Produto Válido",
                "Descrição válida",
                BigDecimal.valueOf(10.0),
                "http://imagem.com/produto.jpg",
                LocalDateTime.now().plusDays(1),
                null
        );

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("A data não pode estar no futuro"));
    }
}

