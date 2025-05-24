package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category getCategoryByName(String name);
}
