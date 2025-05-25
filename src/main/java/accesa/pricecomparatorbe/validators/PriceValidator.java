package accesa.pricecomparatorbe.validators;

import org.springframework.stereotype.Component;

@Component
public class PriceValidator {

    public void validatePrice(Double price) throws ValidationException {
        if (price <= 0) {
            throw new ValidationException("Price can not be negative!");
        }
    }
}
