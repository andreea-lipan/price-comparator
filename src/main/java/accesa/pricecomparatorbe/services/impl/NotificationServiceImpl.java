package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Notification;
import accesa.pricecomparatorbe.model.UserAlert;
import accesa.pricecomparatorbe.persistence.NotificationRepository;
import accesa.pricecomparatorbe.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notifyUserViaSocket(Notification notification) {
        messagingTemplate.convertAndSendToUser(
                notification.getUser().getId().toString(),
                "/queue/notifications",
                notification
        );
    }

    @Override
    public void createNotification(UserAlert alert, MarketProduct prod) {
        Notification notification = Notification.builder()
                .user(alert.getUser())
                .marketProduct(prod)
                .message("Reducere!!")
                .isRead(false)
                .dateCreated(LocalDateTime.now())
                .build();

        notifyUserViaSocket(notification);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUser(Long userId) {
        List<Notification> notis = notificationRepository.getNotificationsByUser_Id(userId);
        notis.sort(Comparator.comparing(Notification::getDateCreated));
        return notis;
    }
}
