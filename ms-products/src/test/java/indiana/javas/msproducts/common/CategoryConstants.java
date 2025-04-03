package indiana.javas.msproducts.common;

import indiana.javas.msproducts.dto.CategoryDto;
import indiana.javas.msproducts.entities.Category;
import indiana.javas.msproducts.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryConstants {

    public static final Category CATEGORY = new Category(1L, "Technology");
    public static final Category INVALID_CATEGORY = new Category(2L, "");
    public static final CategoryDto CATEGORY_DTO = new CategoryDto("Furniture");

}
