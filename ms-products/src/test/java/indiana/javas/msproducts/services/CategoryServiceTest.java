package indiana.javas.msproducts.services;

import static indiana.javas.msproducts.common.CategoryConstants.CATEGORY;
import static indiana.javas.msproducts.common.CategoryConstants.INVALID_CATEGORY;

import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.exceptions.CategoryNotFoundException;
import indiana.javas.msproducts.exceptions.DuplicateCategoryNameException;
import indiana.javas.msproducts.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private Category invalidCategory;

    @BeforeEach
    void setUp() {
        category = CATEGORY;
        invalidCategory = INVALID_CATEGORY;
    }

    @Test
    public void createCategory_WithValidData_ReturnsCategory() {
        when(categoryRepository.save(CATEGORY)).thenReturn(CATEGORY);

        Category sut = categoryService.createCategory(CATEGORY);

        assertThat(sut).isEqualTo(CATEGORY);
    }

    @Test
    public void createCategory_WithInvalidData_ThrowsException() {
        when(categoryRepository.save(INVALID_CATEGORY)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> categoryService.createCategory(INVALID_CATEGORY)).isInstanceOf(RuntimeException.class);
    }


    @Test
    public void createCategory_WithDuplicateName_ThrowsDuplicateCategoryNameException() {
        when(categoryRepository.save(INVALID_CATEGORY)).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> categoryService.createCategory(INVALID_CATEGORY)).isInstanceOf(DuplicateCategoryNameException.class);

        //.hasMessageContaining("Category 'Technology' already exists");
    }


    @Test
    public void getCategory_ByUnexistingId_ThrowsException() {
        List<Category> categories = new ArrayList<>();

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void listCategories_ReturnsAllCategories(){
        List<Category> categories = new ArrayList<>();

        categories.add(CATEGORY);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> sut = categoryService.findAll();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(CATEGORY);
    }

    @Test
    public void listCategories_ReturnsNoCategories(){

        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> sut = categoryService.findAll();

        assertThat(sut).isEmpty();
    }

    /*
    @Test
    public void updateCategory_WithExistingId_ReturnsCategory() {
        Category automotive = new Category(1L, "");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(CATEGORY));
        when(categoryRepository.existsByName(automotive.getName())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(automotive);


        Category sut = categoryService.update(1L, automotive);

        assertThat(sut).isEqualTo(automotive);
        System.out.println(sut);
        assertThat(sut.getName()).isEqualTo("Automotive");
    }
*/

 /*   @Test
    public void updateCategory_WithDuplicateName_ThrowsDuplicateCategoryNameException() {
        Category existingCategory = new Category(1L, "Technology");
        Category categoryToUpdate = new Category(1L, "Duplicate Name");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByName("Duplicate Name")).thenReturn(true);

        assertThatThrownBy(() -> categoryService.update(1L, categoryToUpdate))
                .isInstanceOf(DuplicateCategoryNameException.class);
                //.hasMessageContaining("Category 'Technology' already exists");
    }
*/

    @Test
    public void updateCategory_WithNonExistingId_ThrowsCategoryNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Category automotive = new Category(1L, "Automotive");

        assertThatThrownBy(() -> categoryService.update(1L, automotive))
                .isInstanceOf(CategoryNotFoundException.class);

        //hasMessageContaining("category not found with id '1'");
    }

    @Test
    public void deleteCategory_WithExistingId_notThrowingExceptions(){
        assertThatCode(() -> categoryService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteCategory_WithNonExistingId_ThrowsException(){
        doThrow(new CategoryNotFoundException()).when(categoryRepository).deleteById(777L);

        assertThatThrownBy(() -> categoryService.delete(777L)).isInstanceOf(CategoryNotFoundException.class);
    }

}
