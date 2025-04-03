package indiana.javas.msproducts.repositories;

import static indiana.javas.msproducts.common.CategoryConstants.CATEGORY;

import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.exceptions.DuplicateCategoryNameException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/sql/categories/categories-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/categories/categories-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createCategory_WithValidData_ReturnsCategory() {
        Category category = categoryRepository.save(CATEGORY);

        Category sut = testEntityManager.find(Category.class, category.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getName()).isEqualTo(CATEGORY.getName());

    }

    @Test
    public void createCategory_WithInvalidData_ThrowsException() {
        Category emptyCategory = new Category();

        assertThatThrownBy(() -> categoryRepository.save(emptyCategory)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void createCategory_WithExistingName_ThrowsException() {
        Category duplicateCategory = new Category();
        duplicateCategory.setName(CATEGORY.getName());

        assertThatThrownBy(() -> categoryRepository.save(duplicateCategory))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void existsByName_WhenCategoryExists_ReturnsTrue() {
        boolean exists = categoryRepository.existsByName("Technology");

        assertThat(exists).isTrue();
    }

    @Test
    public void existsByName_WhenCategoryDoesNotExist_ReturnsFalse() {
        boolean exists = categoryRepository.existsByName("Non-Existing Category");

        assertThat(exists).isFalse();
    }

}
