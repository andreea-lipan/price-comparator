package accesa.pricecomparatorbe.services;

import java.util.List;
import org.springframework.stereotype.Service;
import accesa.pricecomparatorbe.model.Category;
import accesa.pricecomparatorbe.dtos.CategoryDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

@Service
public interface CategoryService {

    void addCategory(CategoryDTO categoryDTO) throws ValidationException;

    List<Category> getCategories();

    Category getCategoryById(Long categoryId);
}
