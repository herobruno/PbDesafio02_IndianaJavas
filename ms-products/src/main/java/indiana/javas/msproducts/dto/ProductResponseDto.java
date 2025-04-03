package indiana.javas.msproducts.dto;


import indiana.javas.msproducts.entities.Category;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseDto {

    //@JsonProperty("date")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    // @JsonProperty("description")
    private String description;
    //@JsonProperty("name")
    private String name;
    //@JsonProperty("price")
    private BigDecimal price;
    //@JsonProperty("imgUrl")
    private String imgUrl;

    private Set<CategoryReponseDto> categories;

}

