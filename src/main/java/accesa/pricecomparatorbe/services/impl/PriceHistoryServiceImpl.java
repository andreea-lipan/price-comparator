package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.model.Discount;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.Price;
import accesa.pricecomparatorbe.model.PriceHistory;
import accesa.pricecomparatorbe.persistence.PriceHistoryRepository;
import accesa.pricecomparatorbe.services.PriceHistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryServiceImpl(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Override
    public void createPriceHistory(MarketProduct marketProduct, Price price, Discount discount) {
        PriceHistory priceHistory = PriceHistory.builder()
                .product(marketProduct)
                .priceChanges(new ArrayList<>())
                .discountChanges(new ArrayList<>())
                .build();
        priceHistory.addPrice(price);
        if (discount != null)
            priceHistory.addDiscount(discount);
        priceHistoryRepository.save(priceHistory);
    }

    @Override
    public PriceHistory getHistoryByProduct(MarketProduct product) {
        return priceHistoryRepository.getPriceHistoryByProduct(product)
                .orElseThrow(() -> new EntityNotFoundException("Price history not found!"));
    }

    @Override
    public void updatePriceHistory(Price price, MarketProduct product) {
        PriceHistory history = getHistoryByProduct(product);
        history.addPrice(price);
        priceHistoryRepository.save(history);
    }

    @Override
    public void updateDiscountHistory(Discount discount, MarketProduct product) {
        PriceHistory history = getHistoryByProduct(product);
        history.addDiscount(discount);
        priceHistoryRepository.save(history);
    }

    @Override
    public TreeMap<LocalDate, Double> computeHistoryForMarketProduct(MarketProduct product) {
        PriceHistory history = getHistoryByProduct(product);

        List<Price> prices = history.getPriceChanges();
        List<Discount> discounts = history.getDiscountChanges();

        // Sort both lists by their start dates
        prices.sort(Comparator.comparing(Price::getDateAdded));
        discounts.sort(Comparator.comparing(Discount::getStartDate));

        TreeSet<LocalDate> changeDates = getPrinceFluctuationsDates(prices, discounts);

        // Prepare output map and state tracking
        TreeMap<LocalDate, Double> result = new TreeMap<>();
        Double previousEffectivePrice = null;

        // integer - price index inside the prices list
        // Price - the current price value
        Pair<Integer, Price> currentPrice = Pair.of(0, new Price());
        Pair<Integer, Discount> currentDiscount = Pair.of(0, new Discount());


        for (LocalDate date : changeDates) {
            // Update current price if we passed a new price date
            currentPrice = updateCurrentPrice(prices, date, currentPrice);
            System.out.println(currentPrice);

            // Update current discount
            currentDiscount = updateCurrentDiscount(discounts, date, currentDiscount);
            System.out.println(currentDiscount);

            // Calculate effective price
            double discountFactor = (currentDiscount.getSecond().getValue() != 0.0) ? (1 - currentDiscount.getSecond().getValue() / 100) : 1.0;
            double effectivePrice = currentPrice.getSecond().getValue() * discountFactor;

            // Only insert into map if the price changed
            if (!Objects.equals(effectivePrice, previousEffectivePrice)) {
                result.put(date, effectivePrice);
                previousEffectivePrice = effectivePrice;
            }
        }

        return result;
    }

    private Pair<Integer, Price> updateCurrentPrice(List<Price> prices, LocalDate date, Pair<Integer, Price> currentPrice) {
        int priceIndex = currentPrice.getFirst();
        Price newPrice = currentPrice.getSecond();
        System.out.println("before while" + date);
        while (priceIndex < prices.size() && !prices.get(priceIndex).getDateAdded().isAfter(date)) {
            newPrice = prices.get(priceIndex);
            System.out.println("while price" + newPrice);
            priceIndex++;
        }

        return Pair.of(priceIndex, newPrice);
    }

    private Pair<Integer, Discount> updateCurrentDiscount(List<Discount> discounts, LocalDate date, Pair<Integer, Discount> currentDiscount) {
        int discountIndex = currentDiscount.getFirst();
        Discount newDiscount = new Discount();

        if (discountIndex < discounts.size()) {
            Discount d = discounts.get(discountIndex);
            if (!date.isBefore(d.getStartDate()) && !date.isAfter(d.getEndDate())) {
                newDiscount = d;
            } else if (date.isAfter(d.getEndDate())) {
                newDiscount = new Discount();
                discountIndex++;
                // Look ahead for next discount
                if (discountIndex < discounts.size()) {
                    Discount next = discounts.get(discountIndex);
                    if (!date.isBefore(next.getStartDate()) && !date.isAfter(next.getEndDate())) {
                        newDiscount = next;
                    }
                }
            }
        }

        return Pair.of(discountIndex, newDiscount);
    }

    /**
     * Finds the dates where there are changes in a products price or discount
     *
     * @param prices
     * @param discounts
     * @return
     */
    private TreeSet<LocalDate> getPrinceFluctuationsDates(List<Price> prices, List<Discount> discounts) {
        // Collect all change dates: price changes, discount starts and ends
        TreeSet<LocalDate> changeDates = new TreeSet<>();

        for (Price price : prices) {
            changeDates.add(price.getDateAdded());
        }

        for (Discount discount : discounts) {
            changeDates.add(discount.getStartDate());
            changeDates.add(discount.getEndDate().plusDays(1)); // discount ends after endDate
        }

        return changeDates;
    }
}
