package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.model.Price;
import accesa.pricecomparatorbe.validators.ValidationException;

import java.time.LocalDate;

public interface PriceService {
    Price addPrice(Double price, LocalDate dateAddedPrice) throws ValidationException;
}
