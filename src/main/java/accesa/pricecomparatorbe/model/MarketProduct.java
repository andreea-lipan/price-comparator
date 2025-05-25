package accesa.pricecomparatorbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

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

    public Pair<String, Double> getValuePerUnit() {
        String unit = product.getUnit();
        double price = getPriceWithDiscount();
        double quantity = product.getQuantity();
        double newPrice = 0.0;

        switch (unit) {
            case "g": {
                newPrice = (1000.0 * price) / quantity;
                unit = "kg";
                break;
            }
            case "kg": {
                newPrice = price / quantity;
                unit = "kg";
                break;
            }
            case "ml": {
                newPrice = (1000.0 * price) / quantity;
                unit = "l";
                break;
            }
            case "l": {
                newPrice = price / quantity;
                unit = "l";
                break;
            }
            case "buc": {
                newPrice = price / quantity;
                unit = "buc";
                break;
            }
            default: {
                newPrice = price;
                break;
            }
        }
        return Pair.of(unit, newPrice);
    }
}
