package accesa.pricecomparatorbe.services;

import java.util.List;
import accesa.pricecomparatorbe.model.Currency;
import accesa.pricecomparatorbe.dtos.CurrencyDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

public interface CurrencyService {

    void addCurrency(CurrencyDTO currencyDTO) throws ValidationException;

    List<Currency> getCurrencies();

    Currency getCurrencyById(Long currencyId);
}
