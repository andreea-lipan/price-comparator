package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.model.*;
import accesa.pricecomparatorbe.persistence.*;
import accesa.pricecomparatorbe.services.MarketProductService;
import accesa.pricecomparatorbe.validators.MarketProductValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarketProductServiceImpl implements MarketProductService {

    private final MarketProductRepository marketProductRepository;
    private final MarketProductValidator marketProductValidator;
    private final ProductRepository productRepository;
    private final RetailerRepository retailerRepository;
    private final CurrencyRepository currencyRepository;
    private final DiscountRepository discountRepository;

    public MarketProductServiceImpl(MarketProductRepository marketProductRepository, MarketProductValidator marketProductValidator,
                                ProductRepository productRepository, RetailerRepository retailerRepository, CurrencyRepository currencyRepository,
                                DiscountRepository discountRepository) {
        this.marketProductRepository = marketProductRepository;
        this.marketProductValidator = marketProductValidator;
        this.productRepository = productRepository;
        this.retailerRepository = retailerRepository;
        this.currencyRepository = currencyRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public void addProduct(MarketProductDTO marketProductDTO) throws ValidationException {
        marketProductValidator.validateMarketProductDTO(marketProductDTO);

        Product product = productRepository.findById(marketProductDTO.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found")); //todo is this exceptie okai?

        Retailer retailer = retailerRepository.findById(marketProductDTO.getRetailerId())
            .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Currency currency = currencyRepository.findById(marketProductDTO.getCurrencyId())
            .orElseThrow(() -> new RuntimeException("Currency not found"));

        Discount discount = Discount.builder()
                .startDate(marketProductDTO.getStartDateDiscount())
                .endDate(marketProductDTO.getEndDateDiscount())
                .value(marketProductDTO.getValueDiscount())
                .build();

        discountRepository.save(discount);

        MarketProduct marketProduct = MarketProduct.builder()
            .product(product)
            .price(marketProductDTO.getPrice())
            .currency(currency)
            .retailer(retailer)
            .discount(discount)
            .build();

        marketProductRepository.save(marketProduct);
    }

    @Override
    public List<MarketProduct> getProducts() {
        return marketProductRepository.findAll();
    }
}
