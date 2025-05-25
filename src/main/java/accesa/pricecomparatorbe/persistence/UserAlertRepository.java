package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.model.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAlertRepository extends JpaRepository<UserAlert, Long> {
    List<UserAlert> getUserAlertsByProduct(Product product);
}
