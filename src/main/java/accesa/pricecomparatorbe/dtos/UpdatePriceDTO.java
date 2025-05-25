package accesa.pricecomparatorbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriceDTO {

    private Double price;

    private LocalDate priceAddedDate;
}