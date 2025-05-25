package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.model.Discount;

public interface DiscountService {

    Discount addDiscount(MarketProductDTO dto);

    Discount addDiscount(UpdateDiscountDTO dto);
}
