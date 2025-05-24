package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validateProductDTO(ProductDTO dto) throws ValidationException {
        String errorMessage = "";

        if (dto == null) {
            errorMessage += "Product data must not be null!\n";
        } else {
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                errorMessage += "Product name is required!\n";
            }
            if (dto.getCategoryId() == null || dto.getCategoryId() <= 0) {
                errorMessage += "Product category is required!\n";
            }
            if (dto.getBrandId() == null || dto.getBrandId() <= 0) {
                errorMessage += "Product brand is required!\n";
            }
            if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
                errorMessage += "Product quantity is required and it must be a positive number!\n";
            }
            if (dto.getUnit() == null || dto.getUnit().trim().isEmpty()) {
                errorMessage += "Product unit is required!\n";
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
