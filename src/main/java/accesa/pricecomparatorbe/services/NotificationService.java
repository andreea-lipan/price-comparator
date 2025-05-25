package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Notification;
import accesa.pricecomparatorbe.model.UserAlert;

import java.util.List;

public interface NotificationService {
    void createNotification(UserAlert alert, MarketProduct prod);

    List<Notification> getNotificationsByUser(Long userId);
}
