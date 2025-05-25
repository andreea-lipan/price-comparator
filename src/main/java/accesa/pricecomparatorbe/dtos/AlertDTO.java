package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDTO {

    private Long userId;

    private Long productId;

    private Double threshold;
}
