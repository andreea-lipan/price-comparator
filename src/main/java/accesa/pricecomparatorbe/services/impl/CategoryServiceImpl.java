package accesa.pricecomparatorbe.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import accesa.pricecomparatorbe.services.CategoryService;
import accesa.pricecomparatorbe.dtos.CategoryDTO;
import accesa.pricecomparatorbe.validators.ValidationException;
import accesa.pricecomparatorbe.validators.CategoryValidator;
import accesa.pricecomparatorbe.model.Category;
import accesa.pricecomparatorbe.persistence.CategoryRepository;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
    }

    @Override  
    public void addCategory(CategoryDTO categoryDTO) throws ValidationException {
        categoryValidator.validateCategoryDTO(categoryDTO);

        Category category = Category.builder()
            .name(categoryDTO.getName())
            .build();

        categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
    }
}
