package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.ProductDTO;
import accesa.pricecomparatorbe.model.Product;
import accesa.pricecomparatorbe.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.addProduct(productDTO);
            return ResponseEntity.ok("Product added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            return ResponseEntity.ok(productService.getProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
