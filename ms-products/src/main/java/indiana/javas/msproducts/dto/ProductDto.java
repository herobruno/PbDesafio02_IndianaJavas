package indiana.javas.msproducts.dto;

import indiana.javas.msproducts.entities.Category;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    //@JsonProperty("name")
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String name;

    //@JsonProperty("description")
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String description;

    //@JsonProperty("price")
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal price;

    //@JsonProperty("imgUrl")
    @NotBlank(message = "A URL da imagem é obrigatória")
    private String imgUrl;

    //@JsonProperty("date")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @PastOrPresent(message = "A data não pode estar no futuro")
    private LocalDateTime date;

    private Set<Long> categories;
}
