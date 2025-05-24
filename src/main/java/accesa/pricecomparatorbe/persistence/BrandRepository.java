package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> getBrandByName(String name);
}
