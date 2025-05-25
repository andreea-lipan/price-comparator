package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.dtos.UpdatePriceDTO;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.model.PriceHistory;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.validators.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface MarketProductService {

    void addProduct(MarketProductDTO marketProductDTO) throws ValidationException;

    List<MarketProduct> getProducts();

    MarketProduct getMarketProductById(Long id);

    MarketProduct getCheapestMarketProductForProduct(Product product);

    List<MarketProduct> getProductsWithHighestDiscount(int productPercentage);

    List<MarketProduct> getProductsWithLatestDiscounts();

    void updateProductPrice(Long prodId, UpdatePriceDTO dto) throws ValidationException;

    void updateProductDiscount(Long prodId, UpdateDiscountDTO dto) throws ValidationException;

    PriceHistory getHistoryForProduct(Long id) throws ValidationException;

    Map<MarketProduct, TreeMap<LocalDate, Double>> getHistories(Long retailerId, Long categoryId, Long brandId);
}
