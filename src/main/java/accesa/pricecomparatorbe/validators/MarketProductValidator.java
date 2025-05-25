package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.dtos.UpdatePriceDTO;
import org.springframework.stereotype.Component;

@Component
public class MarketProductValidator {

    public void validateMarketProductDTO(MarketProductDTO dto) throws ValidationException {
        String errorMessage = "";

        if (dto == null) {
            errorMessage += "Market product data must not be null!\n";
        } else {
            if (dto.getProductId() == null || dto.getProductId() <= 0) {
                errorMessage += "Valid product ID is required!\n";
            }
            if (dto.getPrice() == null || dto.getPrice() <= 0) {
                errorMessage += "Price is required and must be a positive number!\n";
            }
            if (dto.getCurrencyId() == null || dto.getCurrencyId() <= 0) {
                errorMessage += "Currency is required!\n";
            }
            if (dto.getRetailerId() == null || dto.getRetailerId() <= 0) {
                errorMessage += "Retailer is required!\n";
            }
            if (dto.getStartDateDiscount() != null && dto.getEndDateDiscount() != null) {
                if (dto.getEndDateDiscount().isBefore(dto.getStartDateDiscount())) {
                    errorMessage += "End date of discount must not be before start date!\n";
                }
            }
            if (dto.getValueDiscount() != null && dto.getValueDiscount() < 0) {
                errorMessage += "Value discount cannot be negative!\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    public void validateUpdatePriceDTO(UpdatePriceDTO dto) throws ValidationException {
        String errorMessage = "";

        if (dto == null) {
            errorMessage += "Market product data must not be null!\n";
        } else {
            if (dto.getPrice() == null || dto.getPrice() <= 0) {
                errorMessage += "Price is required and must be a positive number!\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    public void validateUpdateDiscountDTO(UpdateDiscountDTO dto) throws ValidationException {
        String errorMessage = "";

        if (dto == null) {
            errorMessage += "Market product data must not be null!\n";
        } else {
            if (dto.getStartDateDiscount() == null) {
                errorMessage += "Start date can not be null\n";
            }
            if (dto.getStartDateDiscount() == null) {
                errorMessage += "End date can not be null\n";
            }
            if (dto.getStartDateDiscount() != null && dto.getEndDateDiscount() != null) {
                if (dto.getEndDateDiscount().isBefore(dto.getStartDateDiscount())) {
                    errorMessage += "End date of discount must not be before start date!\n";
                }
            }
            if (dto.getValueDiscount() != null && dto.getValueDiscount() < 0) {
                errorMessage += "Value discount cannot be negative!\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}