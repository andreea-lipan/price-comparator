package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.model.*;
import accesa.pricecomparatorbe.persistence.*;
import accesa.pricecomparatorbe.services.*;
import accesa.pricecomparatorbe.validators.MarketProductValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class MarketProductServiceImpl implements MarketProductService {

    private final MarketProductRepository marketProductRepository;
    private final MarketProductValidator marketProductValidator;
    private final ProductService productService;
    private final RetailerService retailerService;
    private final CurrencyService currencyService;
    private final DiscountService discountService;

    public MarketProductServiceImpl(MarketProductRepository marketProductRepository, MarketProductValidator marketProductValidator,
                                    ProductService productService, RetailerService retailerService, CurrencyService currencyService,
                                    DiscountService discountService) {
        this.marketProductRepository = marketProductRepository;
        this.marketProductValidator = marketProductValidator;
        this.productService = productService;
        this.retailerService = retailerService;
        this.currencyService = currencyService;
        this.discountService = discountService;
    }

    @Override
    public void addProduct(MarketProductDTO marketProductDTO) throws ValidationException {
        marketProductValidator.validateMarketProductDTO(marketProductDTO);

        Product product = productService.getProductById(marketProductDTO.getProductId());
        Retailer retailer = retailerService.getRetailerById(marketProductDTO.getRetailerId());

        // Check if this retailer already has this product
        List<MarketProduct> existingProducts = marketProductRepository.findByRetailerAndProduct(retailer, product);
        if (!existingProducts.isEmpty()) {
            throw new RuntimeException("This retailer already has this product in their catalog");
        }

        Currency currency = currencyService.getCurrencyById(marketProductDTO.getCurrencyId());
        Discount discount = discountService.addDiscount(marketProductDTO);

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

    @Override
    public MarketProduct getCheapestMarketProductForProduct(Product product) {
        return marketProductRepository.getMarketProductsByProduct(product)
                .stream()
                .min(Comparator.comparingDouble(MarketProduct::getPriceWithDiscount))
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find cheapest product!"));
    }
}
