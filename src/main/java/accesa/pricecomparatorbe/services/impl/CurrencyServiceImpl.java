package accesa.pricecomparatorbe.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import accesa.pricecomparatorbe.model.Currency;
import accesa.pricecomparatorbe.persistence.CurrencyRepository;
import accesa.pricecomparatorbe.services.CurrencyService;
import java.util.List;
import accesa.pricecomparatorbe.validators.CurrencyValidator;
import accesa.pricecomparatorbe.dtos.CurrencyDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyValidator currencyValidator;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyValidator currencyValidator) {
        this.currencyRepository = currencyRepository;
        this.currencyValidator = currencyValidator;
    }

    @Override
    public void addCurrency(CurrencyDTO currencyDTO) throws ValidationException {
        currencyValidator.validateCurrencyDTO(currencyDTO);

        Currency currency = Currency.builder()
            .name(currencyDTO.getName())
            .build();

        currencyRepository.save(currency);
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency getCurrencyById(Long currencyId) {
        return currencyRepository.findById(currencyId)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found!"));
    }
}
