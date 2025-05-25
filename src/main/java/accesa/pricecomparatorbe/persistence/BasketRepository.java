package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Basket;
import accesa.pricecomparatorbe.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUser(AppUser user);
}
