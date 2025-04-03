package indiana.javas.msproducts.dto.mapper;

import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.dto.ProductResponseDto;
import indiana.javas.msproducts.entities.Product;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toProduct(ProductDto dto) {
        return new ModelMapper().map(dto, Product.class);
    }

    public static ProductResponseDto toDto(Product product) {
        return new ModelMapper().map(product, ProductResponseDto.class);
    }

    public static List<ProductResponseDto> toListDto(List<Product> products) {
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

}
