package accesa.pricecomparatorbe.dtos;

import accesa.pricecomparatorbe.model.MarketProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithPricePerUnitDTO {

    private MarketProduct product;

    private Double pricePerUnit;

    private String unit;
}
