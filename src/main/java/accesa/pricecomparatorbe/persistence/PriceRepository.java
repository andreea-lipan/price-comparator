package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
