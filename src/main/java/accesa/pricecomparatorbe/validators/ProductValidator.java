package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validateProductDTO(ProductDTO productDTO) throws ValidationException {
        String errorMessage = "";

        if (productDTO == null) {
            errorMessage += "Product data must not be null!\n";
        } else {
            if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
                errorMessage += "Product name is required!\n";
            }
            if (productDTO.getCategory() == null || productDTO.getCategory().trim().isEmpty()) {
                errorMessage += "Product category is required!\n";
            }
            if (productDTO.getBrand() == null || productDTO.getBrand().trim().isEmpty()) {
                errorMessage += "Product brand is required!\n";
            }
            if (productDTO.getQuantity() == null || productDTO.getQuantity().compareTo(0.0) <= 0) {
                errorMessage += "Product quantity is required and it must be a positive number!\n";
            }
            if (productDTO.getUnit() == null || productDTO.getUnit().trim().isEmpty()) {
                errorMessage += "Product unit is required!\n";
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
