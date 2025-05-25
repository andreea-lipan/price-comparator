package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.AlertDTO;
import accesa.pricecomparatorbe.model.Product;

public interface UserAlertService {
    void createAlert(AlertDTO dto);

    void checkAlertsForProducts(Product prod);
}
