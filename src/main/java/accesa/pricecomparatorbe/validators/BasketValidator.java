package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.BasketDTO;
import org.springframework.stereotype.Component;

@Component
public class BasketValidator {

    public void validateBasketDTO(BasketDTO dto) throws ValidationException {
        String errorMessage = "";

        if (dto == null) {
            errorMessage += "Basket data must not be null!\n";
        } else {
            if (dto.getUserId() == null) {
                errorMessage += "User ID is required!\n";
            }
            if (dto.getProductIds() == null || dto.getProductIds().isEmpty()) {
                errorMessage += "At least one product ID is required!\n";
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
