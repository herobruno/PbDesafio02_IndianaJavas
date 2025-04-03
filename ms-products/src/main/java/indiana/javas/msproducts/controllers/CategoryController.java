package indiana.javas.msproducts.controllers;

import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.dto.CategoryReponseDto;
import indiana.javas.msproducts.dto.mapper.CategoryMapper;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.services.CategoryService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryReponseDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(CategoryMapper.toCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toDto(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryReponseDto> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryDto item) {
        Category category = categoryService.update(id, CategoryMapper.toCategory(item));
        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReponseDto> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryReponseDto>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(CategoryMapper.toListDto(categories));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
