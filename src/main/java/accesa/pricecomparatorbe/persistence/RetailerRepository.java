package accesa.pricecomparatorbe.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import accesa.pricecomparatorbe.model.Retailer;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
}
