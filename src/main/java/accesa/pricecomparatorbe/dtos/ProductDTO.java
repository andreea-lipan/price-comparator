package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;

    private Long categoryId;

    private Long brandId;

    private Double quantity;

    private String unit;
}
