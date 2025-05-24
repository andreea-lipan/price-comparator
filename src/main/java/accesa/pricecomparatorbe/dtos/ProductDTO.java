package accesa.pricecomparatorbe.dtos;

import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.model.Category;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;

    private String category;

    private String brand;

    private Double quantity;

    private String unit;
}
