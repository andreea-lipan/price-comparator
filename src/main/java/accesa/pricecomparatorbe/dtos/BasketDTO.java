package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDTO {

    private Long userId;
    private List<Long> productIds;
}
