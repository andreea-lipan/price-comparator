package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
