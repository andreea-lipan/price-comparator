package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.services.MarketProductService;
import accesa.pricecomparatorbe.services.ProductService;
import accesa.pricecomparatorbe.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import accesa.pricecomparatorbe.services.BasketService;
import accesa.pricecomparatorbe.dtos.BasketDTO;
import accesa.pricecomparatorbe.validators.ValidationException;
import accesa.pricecomparatorbe.validators.BasketValidator;
import accesa.pricecomparatorbe.model.Basket;
import accesa.pricecomparatorbe.model.AppUser;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Retailer;
import accesa.pricecomparatorbe.persistence.BasketRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketValidator basketValidator;
    private final UserService userService;
    private final ProductService productService;
    private final MarketProductService marketProductService;

    public BasketServiceImpl(BasketRepository basketRepository, BasketValidator basketValidator,
                           UserService userService, ProductService productService,
                           MarketProductService marketProductService) {
        this.basketRepository = basketRepository;
        this.basketValidator = basketValidator;
        this.userService = userService;
        this.productService = productService;
        this.marketProductService = marketProductService;
    }

    @Override
    public Basket getBasketByUserId(Long userId) {
        AppUser user = userService.getUserById(userId);

        return basketRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found for user!"));
    }

    @Override
    public Basket getBasketByUser(AppUser user) {
        return basketRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found for user!"));
    }

    @Override
    public void addToBasket(BasketDTO basketDTO) throws ValidationException {
        basketValidator.validateBasketDTO(basketDTO);

        AppUser user = userService.getUserById(basketDTO.getUserId());
        Basket basket;

        try {
            basket = getBasketByUser(user);
        } catch (EntityNotFoundException e) {
            // If the basket doesn't already exist, we create it
            basket = Basket.builder()
                    .user(user)
                    .products(new ArrayList<>())
                    .build();
        }

        // Add new products to the basket
        for (Long productId : basketDTO.getProductIds()) {
            Product product = productService.getProductById(productId);
            basket.getProducts().add(product);
        }

        basketRepository.save(basket);
    }

    // map by an arbitrary String criteria and explicitly by retailer so its easily extensible
    @Override
    public Map<String, List<MarketProduct>> getShoppingLists(Long userId) {

        // assumption: all products only use the RON currency, if we had more currencies
        // we would need also need a way to translate between currencies
        // to be able to find the best price
        Basket basket = getBasketByUserId(userId);

        if (basket.getProducts().isEmpty()) {
            throw new RuntimeException("There are no items in your basket!");
        }

        Map<Retailer, List<MarketProduct>> cheapProducts = splitTheBasketByRetailers(basket);

        // Convert the map to use retailer names as keys
        return cheapProducts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        Map.Entry::getValue
                ));
    }

    private Map<Retailer, List<MarketProduct>> splitTheBasketByRetailers(Basket basket) {

        // Get all market products for the products in the basket
        List<Product> products = basket.getProducts();
        Map<Retailer, List<MarketProduct>> cheapProducts = new HashMap<>();

        for (Product product : products) {
            MarketProduct cheapProduct;
            try {
                cheapProduct = marketProductService.getCheapestMarketProductForProduct(product);
            } catch (EntityNotFoundException e) {
                continue;
            }

            // add it to the shopping list matching the retailer
            Retailer retailer = cheapProduct.getRetailer();
            cheapProducts.computeIfAbsent(retailer, key -> new ArrayList<>())
                    .add(cheapProduct);
        }

        return cheapProducts;
    }
}
