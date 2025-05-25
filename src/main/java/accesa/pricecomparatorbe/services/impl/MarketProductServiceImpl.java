package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.ProductWithPricePerUnitDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.dtos.UpdatePriceDTO;
import accesa.pricecomparatorbe.model.Currency;
import accesa.pricecomparatorbe.model.*;
import accesa.pricecomparatorbe.persistence.MarketProductRepository;
import accesa.pricecomparatorbe.services.*;
import accesa.pricecomparatorbe.validators.MarketProductValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MarketProductServiceImpl implements MarketProductService {

    private final MarketProductRepository marketProductRepository;
    private final MarketProductValidator marketProductValidator;
    private final ProductService productService;
    private final RetailerService retailerService;
    private final CurrencyService currencyService;
    private final DiscountService discountService;
    private final PriceService priceService;
    private final PriceHistoryService priceHistoryService;

    public MarketProductServiceImpl(MarketProductRepository marketProductRepository, MarketProductValidator marketProductValidator,
                                    ProductService productService, RetailerService retailerService, CurrencyService currencyService,
                                    DiscountService discountService, PriceService priceService, PriceHistoryService priceHistoryService) {
        this.marketProductRepository = marketProductRepository;
        this.marketProductValidator = marketProductValidator;
        this.productService = productService;
        this.retailerService = retailerService;
        this.currencyService = currencyService;
        this.discountService = discountService;
        this.priceService = priceService;
        this.priceHistoryService = priceHistoryService;
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

        Discount discount = null;
        if (marketProductDTO.getStartDateDiscount() != null && marketProductDTO.getEndDateDiscount() != null && marketProductDTO.getValueDiscount() != 0.0)
            discount = discountService.addDiscount(marketProductDTO);

        Price price = priceService.addPrice(marketProductDTO.getPrice(), marketProductDTO.getDateAddedPrice());

        MarketProduct marketProduct = MarketProduct.builder()
                .product(product)
                .currentPrice(price)
                .currency(currency)
                .retailer(retailer)
                .discount(discount)
                .build();

        marketProductRepository.save(marketProduct);
        priceHistoryService.createPriceHistory(marketProduct, price, discount);
    }

    @Override
    public List<MarketProduct> getProducts() {
        return marketProductRepository.findAll();
    }

    @Override
    public MarketProduct getMarketProductById(Long id) {
        return marketProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find the product!"));
    }

    @Override
    public MarketProduct getCheapestMarketProductForProduct(Product product) {
        return marketProductRepository.getMarketProductsByProduct(product)
                .stream()
                .min(Comparator.comparingDouble(MarketProduct::getPriceWithDiscount))
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find cheapest product!"));
    }

    /**
     * Finds the top productPercentage% of products which have the highest discounts across all retailers
     *
     * @param productPercentage the amount of products to be returned in percentage form
     * @return - the list of products with the highest discount across all retailers
     */
    @Override
    public List<MarketProduct> getProductsWithHighestDiscount(int productPercentage) {
        List<MarketProduct> products = getProducts();

        // Only keep the products with discounts still available
        // Sort products by discount value in descending order
        List<MarketProduct> filtered = products.stream()
                .filter(MarketProduct::hasActiveDiscount)
                .toList();

        // Calculate number of products based on percentage
        int nrOfProducts = (int) Math.ceil((productPercentage / 100.0) * filtered.size());

        return filtered.stream()
                .sorted(Comparator.comparing(
                        prod -> prod.getDiscount().getValue(),
                        Comparator.reverseOrder()
                ))
                .limit(nrOfProducts)
                .toList();
    }

    @Override
    public List<MarketProduct> getProductsWithLatestDiscounts() {
        List<MarketProduct> products = getProducts();

        return products.stream()
                .filter(prod -> prod.hasActiveDiscount() &&
                        !prod.getDiscount().getStartDate().isBefore(LocalDate.now().minusDays(1)))
                .toList();
    }

    @Override
    public void updateProductPrice(Long prodId, UpdatePriceDTO dto) throws ValidationException {
        marketProductValidator.validateUpdatePriceDTO(dto);

        Price price = priceService.addPrice(dto.getPrice(), dto.getPriceAddedDate());

        // update market prod value
        MarketProduct product = getMarketProductById(prodId);
        product.setCurrentPrice(price);
        marketProductRepository.save(product);

        // add the price to history
        priceHistoryService.updatePriceHistory(price, product);
    }

    @Override
    public void updateProductDiscount(Long prodId, UpdateDiscountDTO dto) throws ValidationException {
        marketProductValidator.validateUpdateDiscountDTO(dto);

        Discount discount = discountService.addDiscount(dto);

        MarketProduct product = getMarketProductById(prodId);
        product.setDiscount(discount);
        marketProductRepository.save(product);

        priceHistoryService.updateDiscountHistory(discount, product);
    }

    @Override
    public PriceHistory getHistoryForProduct(Long id) {
        MarketProduct product = getMarketProductById(id);
        return priceHistoryService.getHistoryByProduct(product);
    }

    private boolean filterByRetailer(Long retailerId, MarketProduct prod) {
        if (retailerId == -1)
            return true;
        return Objects.equals(prod.getRetailer().getId(), retailerId);
    }

    private boolean filterByCategory(Long categoryId, MarketProduct prod) {
        if (categoryId == -1)
            return true;
        return Objects.equals(prod.getProduct().getCategory().getId(), categoryId);
    }

    private boolean filterByBrand(Long brandId, MarketProduct prod) {
        if (brandId == -1)
            return true;
        return Objects.equals(prod.getProduct().getCategory().getId(), brandId);
    }

    @Override
    public Map<MarketProduct, TreeMap<LocalDate, Double>> getHistories(Long retailerId, Long categoryId, Long brandId) {
        Map<MarketProduct, TreeMap<LocalDate, Double>> prices = new HashMap<>();
        List<MarketProduct> products = getProducts().stream()
                .filter(p -> filterByRetailer(retailerId, p))
                .filter(p -> filterByCategory(categoryId, p))
                .filter(p -> filterByBrand(brandId, p))
                .toList();
        for (MarketProduct p : products) {
            prices.put(p, priceHistoryService.computeHistoryForMarketProduct(p));
        }
        return prices;
    }

    @Override
    public List<ProductWithPricePerUnitDTO> getProductsWithPricePerUnit() {
        List<MarketProduct> products = getProducts();
        List<ProductWithPricePerUnitDTO> unitProducts = new ArrayList<>();

        System.out.println("Managed to grad =rods");
        System.out.println(products);

        for (MarketProduct p : products) {
            Pair<String, Double> value = p.getValuePerUnit();
            ProductWithPricePerUnitDTO unitProd = ProductWithPricePerUnitDTO.builder()
                    .product(p)
                    .unit(value.getFirst())
                    .pricePerUnit(value.getSecond())
                    .build();
            unitProducts.add(unitProd);
        }

        System.out.println("Return");
        return unitProducts;
    }
}
