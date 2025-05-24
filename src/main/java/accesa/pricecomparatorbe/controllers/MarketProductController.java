package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.ProductDTO;
import accesa.pricecomparatorbe.model.MarketProduct;
import accesa.pricecomparatorbe.services.MarketProductService;
import accesa.pricecomparatorbe.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/market-product")
public class MarketProductController {

    private final MarketProductService marketProductService;

    public MarketProductController(MarketProductService marketProductService) {
        this.marketProductService = marketProductService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody MarketProductDTO marketProductDTO) {
        try {
            marketProductService.addProduct(marketProductDTO);
            return ResponseEntity.ok("Product added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            return ResponseEntity.ok(marketProductService.getProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
