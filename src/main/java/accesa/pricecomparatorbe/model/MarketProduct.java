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

    private Double price;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Retailer retailer;

    @ManyToOne
    private Discount discount;

    public double getPriceWithDiscount() {
        if (discount != null) {
            return price * (1 - discount.getValue() / 100);
        }
        return price;
    }

    public boolean hasActiveDiscount() {
        return discount != null && discount.isDiscountCurrent();
    }
}
