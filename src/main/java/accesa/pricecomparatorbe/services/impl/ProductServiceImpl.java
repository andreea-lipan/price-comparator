package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.model.Category;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.persistence.BrandRepository;
import accesa.pricecomparatorbe.persistence.CategoryRepository;
import accesa.pricecomparatorbe.persistence.ProductRepository;
import accesa.pricecomparatorbe.services.ProductService;
import accesa.pricecomparatorbe.validators.ProductValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl (ProductRepository productRepository, ProductValidator productValidator,
                               CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public void addProduct(ProductDTO productDTO) throws ValidationException {
        productValidator.validateProductDTO(productDTO);

        Category category = categoryRepository.getCategoryByName(productDTO.getCategory());
        Brand brand = brandRepository.getBrandByName(productDTO.getBrand());

        Product product = Product.builder()
                .name(productDTO.getName())
                .category(category)
                .brand(brand)
                .quantity(productDTO.getQuantity())
                .unit(productDTO.getUnit())
                .build();

        productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
