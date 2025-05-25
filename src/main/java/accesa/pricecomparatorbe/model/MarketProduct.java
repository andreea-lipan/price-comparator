package accesa.pricecomparatorbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MarketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @OneToOne
    private Price currentPrice;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Retailer retailer;

    @OneToOne
    private Discount discount;

    public double getPriceWithDiscount() {
        if (discount != null) {
            return currentPrice.getValue() * (1 - discount.getValue() / 100);
        }
        return currentPrice.getValue();
    }

    public boolean hasActiveDiscount() {
        return discount != null && discount.isDiscountCurrent();
    }
}
