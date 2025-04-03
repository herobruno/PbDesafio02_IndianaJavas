package indiana.javas.msproducts.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static indiana.javas.msproducts.common.CategoryConstants.CATEGORY;

import com.fasterxml.jackson.databind.ObjectMapper;
import indiana.javas.msproducts.controllers.CategoryController;
import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.dto.mapper.CategoryMapper;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.exceptions.DuplicateCategoryNameException;
import indiana.javas.msproducts.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    private CategoryDto categoryDto;

    private CategoryDto invalidCategoryDto;

    private CategoryDto emptyCategoryDto;

    @BeforeEach
    public void setUp() {
        categoryDto = new CategoryDto("Food");
        invalidCategoryDto = new CategoryDto("");
        emptyCategoryDto = new CategoryDto();
    }

   /* @Test
    public void createCategory_WithValidData_ReturnsCreated() throws Exception {
        when(categoryService.createCategory(CATEGORY)).thenReturn(CATEGORY);

        mockMvc.perform(post("/api/v1/categories").content(objectMapper.writeValueAsString(CATEGORY))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(CATEGORY));
    } */

    @Test
    public void createCategory_WithValidData_ReturnsCreated() throws Exception {

        when(categoryService.createCategory(any(Category.class)))
                .thenReturn(new Category(1L, "Food"));

        mockMvc.perform(post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void createCategory_WithInvalidData_ReturnsStatus422() throws Exception {

        mockMvc
                .perform(
                        post("/api/v1/categories").content(objectMapper.writeValueAsString(emptyCategoryDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
        mockMvc
                .perform(
                        post("/api/v1/categories").content(objectMapper.writeValueAsString(invalidCategoryDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createCategory_WithExistingName_ReturnsStatus409() throws Exception {
        when(categoryService.createCategory(any())).thenThrow(DuplicateCategoryNameException.class);

        mockMvc
                .perform(
                        post("/api/v1/categories").content(objectMapper.writeValueAsString(categoryDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getCategory_ByExistingId_ReturnsStatus200() throws Exception {
        when(categoryService.findById(1L)).thenReturn(CATEGORY);

        mockMvc
                .perform(
                        get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(CATEGORY));
    }

 /*   @Test
    public void getCategory_ByNonExistingId_ReturnsStatus404() throws Exception {

        mockMvc.perform(get("/api/v1/categories/777"))
                .andExpect(status().isNotFound());
    }
*/

    @Test
    public void listCategories_ReturnsListOfCategories() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of(CATEGORY));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(CATEGORY));
    }

    @Test
    public void listCategories_WithEmptyList_ReturnStatus200() throws Exception {
        when(categoryService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

/*
    @Test
    public void updateCategory_WithValidData_ReturnsStatus200() throws Exception {
        CategoryDto updatedCategoryDto = new CategoryDto("Updated Food");
        Category updatedCategory = new Category(1L, "Updated Food");

        when(categoryService.update(1L, CategoryMapper.toCategory(updatedCategoryDto)))
                .thenReturn(updatedCategory);

        mockMvc.perform(put("/api/v1/categories/1")
                        .content(objectMapper.writeValueAsString(updatedCategoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Food"));
    }

    @Test
    public void updateCategory_WithInvalidData_ReturnsStatus422() throws Exception {
        CategoryDto invalidCategoryDto = new CategoryDto("");

        mockMvc.perform(put("/api/v1/categories/1")
                        .content(objectMapper.writeValueAsString(invalidCategoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateCategory_WithNonExistingId_ReturnsStatus404() throws Exception {
        CategoryDto categoryDto = new CategoryDto("Non-existent Category");

        when(categoryService.update(777L, CategoryMapper.toCategory(categoryDto)))
                .thenThrow(new CategoryNotFoundException("Category not found"));

        mockMvc.perform(put("/api/v1/categories/77")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
*/
    @Test
    public void updateCategory_WithDuplicateName_ReturnsStatus409() throws Exception {
        CategoryDto categoryDto = new CategoryDto("Duplicate Category");

        when(categoryService.update(1L, CategoryMapper.toCategory(categoryDto)))
                .thenThrow(new DuplicateCategoryNameException("Category name already exists"));

        mockMvc.perform(put("/api/v1/categories/1")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteCategory_WithExistingId_ReturnsStatus204() throws Exception {
        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isNoContent());
    }

 /*   @Test
    public void removeCategory_WithNonExistingId_ReturnsStatus204() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(categoryService).delete(777L);

        mockMvc.perform(delete("/api/v1/categories/777"))
                .andExpect(status().isNotFound());
    }
*/

}