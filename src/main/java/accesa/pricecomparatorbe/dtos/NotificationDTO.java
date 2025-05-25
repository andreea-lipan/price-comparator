package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;

    private Long marketProductId;

    private String message;

    private boolean isRead;

    private LocalDateTime dateCreated;
}