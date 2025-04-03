package indiana.javas.msproducts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 50, message = "O nome da categoria deve ter no máximo 50 caracteres")
    private String name;

}
