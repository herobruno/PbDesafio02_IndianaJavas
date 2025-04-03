package indiana.javas.msproducts.dto.mapper;

import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.dto.CategoryReponseDto;
import indiana.javas.msproducts.entities.Category;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category toCategory(CategoryDto dto) {
        return new ModelMapper().map(dto, Category.class);
    }

    public static CategoryReponseDto toDto(Category category) {
        return new ModelMapper().map(category, CategoryReponseDto.class);
    }

    public static List<CategoryReponseDto> toListDto(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
