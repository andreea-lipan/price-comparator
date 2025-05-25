package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.model.Discount;
import accesa.pricecomparatorbe.persistence.DiscountRepository;
import accesa.pricecomparatorbe.services.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountServiceImpl (DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount addDiscount(MarketProductDTO dto) {
        Discount discount = Discount.builder()
                .startDate(dto.getStartDateDiscount())
                .endDate(dto.getEndDateDiscount())
                .value(dto.getValueDiscount())
                .build();

        return discountRepository.save(discount);
    }

    @Override
    public Discount addDiscount(UpdateDiscountDTO dto) {
        Discount discount = Discount.builder()
                .startDate(dto.getStartDateDiscount())
                .endDate(dto.getEndDateDiscount())
                .value(dto.getValueDiscount())
                .build();

        return discountRepository.save(discount);
    }
}
