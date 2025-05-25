package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.model.Category;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.persistence.BrandRepository;
import accesa.pricecomparatorbe.persistence.CategoryRepository;
import accesa.pricecomparatorbe.persistence.ProductRepository;
import accesa.pricecomparatorbe.services.BrandService;
import accesa.pricecomparatorbe.services.CategoryService;
import accesa.pricecomparatorbe.services.ProductService;
import accesa.pricecomparatorbe.validators.ProductValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductServiceImpl (ProductRepository productRepository, ProductValidator productValidator,
                               CategoryService categoryService, BrandService brandService) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    public void addProduct(ProductDTO productDTO) throws ValidationException {
        productValidator.validateProductDTO(productDTO);

        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        Brand brand = brandService.getBrandById(productDTO.getBrandId());

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

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

    }
}
