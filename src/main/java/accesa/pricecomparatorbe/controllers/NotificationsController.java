package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.UserTokenDTO;
import accesa.pricecomparatorbe.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/notis")
public class NotificationsController {

    private final NotificationService notificationService;

    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestBody UserTokenDTO dto) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUser(dto.userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
