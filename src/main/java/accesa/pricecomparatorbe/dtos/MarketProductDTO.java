package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketProductDTO {

    private Long productId;

    private Double price;

    private LocalDate dateAddedPrice;

    private Long currencyId;

    private Long retailerId;

    private LocalDate startDateDiscount;

    private LocalDate endDateDiscount;

    private Double valueDiscount;
}
