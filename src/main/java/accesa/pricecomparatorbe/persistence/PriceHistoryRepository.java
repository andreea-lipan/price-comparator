package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    Optional<PriceHistory> getPriceHistoryByProduct(MarketProduct product);
}
