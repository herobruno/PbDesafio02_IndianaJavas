package indiana.javas.msproducts.repositories;

import indiana.javas.msproducts.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
