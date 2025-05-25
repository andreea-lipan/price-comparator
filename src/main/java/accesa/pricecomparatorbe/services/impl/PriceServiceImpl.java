package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.model.Price;
import accesa.pricecomparatorbe.persistence.PriceRepository;
import accesa.pricecomparatorbe.services.PriceService;
import accesa.pricecomparatorbe.validators.PriceValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final PriceValidator priceValidator;

    public PriceServiceImpl(PriceRepository priceRepository, PriceValidator priceValidator) {
        this.priceRepository = priceRepository;
        this.priceValidator = priceValidator;
    }

    @Override
    public Price addPrice(Double price, LocalDate dateAddedPrice) throws ValidationException {
        priceValidator.validatePrice(price);

        Price newPrice = Price.builder()
                .value(price)
                .dateAdded(dateAddedPrice == null ? LocalDate.now() : dateAddedPrice)
                .build();
        return priceRepository.save(newPrice);
    }
}
