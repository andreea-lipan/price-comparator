package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.model.Discount;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Price;
import accesa.pricecomparatorbe.model.PriceHistory;

import java.time.LocalDate;
import java.util.TreeMap;

public interface PriceHistoryService {

    void createPriceHistory(MarketProduct marketProduct, Price price, Discount discount);

    PriceHistory getHistoryByProduct(MarketProduct product);

    void updatePriceHistory(Price price, MarketProduct product);

    void updateDiscountHistory(Discount discount, MarketProduct product);

    TreeMap<LocalDate, Double> computeHistoryForMarketProduct(MarketProduct product);
}
