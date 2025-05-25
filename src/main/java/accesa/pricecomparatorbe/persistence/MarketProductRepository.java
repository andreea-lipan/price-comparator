package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketProductRepository extends JpaRepository<MarketProduct, Long> {

    List<MarketProduct> getMarketProductsByProduct(Product product);

    List<MarketProduct> findByRetailerAndProduct(Retailer retailer, Product product);
}
