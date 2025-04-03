package indiana.javas.msproducts.controllers;

import indiana.javas.msproducts.dto.ProductDto;
import indiana.javas.msproducts.dto.ProductResponseDto;
import indiana.javas.msproducts.dto.mapper.ProductMapper;
import indiana.javas.msproducts.entities.Product;
import indiana.javas.msproducts.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;



@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductControllers {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductDto dto, HttpServletRequest request) {
        var token = getTokenFromRequest(request);
        Product savedProduct = productService.createProduct(dto, token);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(ProductMapper.toDto(savedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isRemoved = productService.deleteProduct(id);

        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int linesPerPage,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.fromString(direction), orderBy);
        return productService.getProducts(pageRequest);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null) {
            return tokenHeader.replace("Bearer ", "");
        }
        return null;
    }
}

