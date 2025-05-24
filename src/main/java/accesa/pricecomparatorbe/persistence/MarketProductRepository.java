package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.MarketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketProductRepository extends JpaRepository<MarketProduct, Long> {
}
