package accesa.pricecomparatorbe.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import accesa.pricecomparatorbe.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
