package indiana.javas.msproducts.services;

import indiana.javas.msproducts.exceptions.CategoryNotFoundException;
import indiana.javas.msproducts.exceptions.DuplicateCategoryNameException;
import indiana.javas.msproducts.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private static final String DUPLICATE_MESSAGE = "Category '%s' already exists";

    @Transactional
    public Category createCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCategoryNameException(String.format(DUPLICATE_MESSAGE , category.getName()));
        }
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("category not found with id '%s' ", id)));
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category update(Long id, Category item) {
        Category category = findById(id);

        boolean nameAlreadyExists = categoryRepository.existsByName(item.getName());
        if (nameAlreadyExists) {
            throw new DuplicateCategoryNameException(String.format(DUPLICATE_MESSAGE , item.getName()));
        }

        category.setName(item.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
