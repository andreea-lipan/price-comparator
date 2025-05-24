package accesa.pricecomparatorbe.validators;

import org.springframework.stereotype.Component;
import accesa.pricecomparatorbe.dtos.RetailerDTO;

@Component
public class RetailerValidator {

    public void validateRetailerDTO(RetailerDTO retailerDTO) throws ValidationException {
        String errorMessage = "";

        if (retailerDTO == null) {
            errorMessage = "Retailer is required";
        } else {
            if (retailerDTO.getName() == null || retailerDTO.getName().isEmpty()) {
                errorMessage = "Retailer name is required";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
