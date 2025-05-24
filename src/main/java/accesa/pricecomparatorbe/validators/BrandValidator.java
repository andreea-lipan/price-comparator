package accesa.pricecomparatorbe.validators;

import org.springframework.stereotype.Component;
import accesa.pricecomparatorbe.dtos.BrandDTO;

@Component
public class BrandValidator {

    public void validateBrandDTO(BrandDTO brandDTO) throws ValidationException {
        String errorMessage = "";

        if (brandDTO == null) {
            errorMessage = "Brand is required";
        } else {
            if (brandDTO.getName() == null || brandDTO.getName().isEmpty()) {
                errorMessage = "Brand name is required";
            }

            if (brandDTO.getName() == null || brandDTO.getName().length() > 255) {
                errorMessage = "Brand name must be less than 255 characters";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
