package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public Brand getBrandByName(String name);
}
