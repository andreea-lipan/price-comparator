package accesa.pricecomparatorbe.validators;

import org.springframework.stereotype.Component;
import accesa.pricecomparatorbe.dtos.CurrencyDTO;

@Component  
public class CurrencyValidator {

    public void validateCurrencyDTO(CurrencyDTO currencyDTO) throws ValidationException {

        String errorMessage = "";

        if (currencyDTO == null) {
            errorMessage = "Currency is required";
        } else {
            if (currencyDTO.getName() == null || currencyDTO.getName().isEmpty()) {
                errorMessage = "Currency name is required";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
