package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getNotificationsByUser_Id(Long id);
}
