package accesa.pricecomparatorbe.validators;

import org.springframework.stereotype.Component;
import accesa.pricecomparatorbe.dtos.CategoryDTO;

@Component
public class CategoryValidator {

    public void validateCategoryDTO(CategoryDTO categoryDTO) throws ValidationException {
        String errorMessage = "";

        if (categoryDTO == null) {
            errorMessage = "Category is required";
        } else {
            if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
                errorMessage = "Category name is required";
            }

            if (categoryDTO.getName() == null || categoryDTO.getName().length() > 255) {
                errorMessage = "Category name must be less than 255 characters";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }   
}
