package indiana.javas.msproducts.services;
import indiana.javas.msproducts.dto.mapper.CategoryMapper;
import indiana.javas.msproducts.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import indiana.javas.msproducts.entities.Product;
import indiana.javas.msproducts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import indiana.javas.msproducts.repositories.CategoryRepository;
import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.exceptions.ResourceNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import indiana.javas.msproducts.entities.Category;



@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final JwtTokenService tokenService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.tokenService = new JwtTokenService();
    }

    @Transactional
    public Product createProduct(ProductDto dto, String token) {

        Set<Category> categories = dto.getCategories().stream()
                .map(category -> categoryRepository.findById(category)
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o id: " + category)))
                .collect(Collectors.toSet());

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());
        product.setDate(dto.getDate());
        product.setCategories(categories);

        var userEmail = tokenService.getUserEmailFromToken(token);
        product.setCreatedBy(userEmail);

        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
    }
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com ID: " + id));
    }

    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }
}
