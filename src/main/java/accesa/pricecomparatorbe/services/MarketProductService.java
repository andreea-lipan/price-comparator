package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.validators.ValidationException;
import java.util.List;

public interface MarketProductService {

    void addProduct(MarketProductDTO marketProductDTO) throws ValidationException;

    List<MarketProduct> getProducts();
}
