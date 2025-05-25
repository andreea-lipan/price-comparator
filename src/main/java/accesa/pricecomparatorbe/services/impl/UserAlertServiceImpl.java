package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.AlertDTO;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.model.UserAlert;
import accesa.pricecomparatorbe.persistence.UserAlertRepository;
import accesa.pricecomparatorbe.services.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAlertServiceImpl implements UserAlertService {

    private final UserAlertRepository alertRepository;
    private final UserService userService;
    private final ProductService productService;
    private final MarketProductService marketProductService;
    private final UserAlertRepository userAlertRepository;
    private final NotificationService notificationService;

    public UserAlertServiceImpl(UserAlertRepository alertRepository, UserService userService,
                                ProductService productService, MarketProductService marketProductService, UserAlertRepository userAlertRepository, NotificationService notificationService) {
        this.alertRepository = alertRepository;
        this.userService = userService;
        this.productService = productService;
        this.marketProductService = marketProductService;
        this.userAlertRepository = userAlertRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void createAlert(AlertDTO dto) {
        UserAlert alert = UserAlert.builder()
                .user(userService.getUserById(dto.getUserId()))
                .product(productService.getProductById(dto.getProductId()))
                .threshold(dto.getThreshold())
                .build();
        alert = alertRepository.save(alert);
    }

    private List<UserAlert> getAlertsByProduct(Product product) {
        return userAlertRepository.getUserAlertsByProduct(product);
    }

    @Override
    public void checkAlertsForProducts(Product prod) {
        List<MarketProduct> prods = marketProductService.getMarketProductByProduct(prod);
        List<UserAlert> alerts = getAlertsByProduct(prod);

        for (UserAlert alert : alerts) {
            for (MarketProduct product : prods) {
                if (product.getPriceWithDiscount() <= alert.getThreshold()) {
                    notificationService.createNotification(alert, product);
                }
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 10 * 1) // 5 minute
    public void alertChecks() {
        System.out.println("Checking alerts...");
        List<Product> prods = productService.getProducts();
        for (Product prod : prods) {
            checkAlertsForProducts(prod);
        }
    }
}
