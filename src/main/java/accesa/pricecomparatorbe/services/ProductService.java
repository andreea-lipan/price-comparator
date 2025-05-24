package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.validators.ValidationException;

import java.util.List;

public interface ProductService {

    void addProduct(ProductDTO productDTO) throws ValidationException;

    List<Product> getProducts();
}
