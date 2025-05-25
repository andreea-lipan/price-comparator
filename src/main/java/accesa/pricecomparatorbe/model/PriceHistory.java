package accesa.pricecomparatorbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private MarketProduct product;

    @OneToMany
    private List<Price> priceChanges;

    @OneToMany
    private List<Discount> discountChanges;

    public void addPrice(Price price) {
        priceChanges.add(price);
    }

    public void addDiscount(Discount discount) {
        if (discount != null) {
            discountChanges.add(discount);
        }
    }
}
