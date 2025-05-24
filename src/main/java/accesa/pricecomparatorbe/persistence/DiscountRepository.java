package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
