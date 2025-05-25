package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.BestDiscountDTO;
import accesa.pricecomparatorbe.dtos.MarketProductDTO;
import accesa.pricecomparatorbe.dtos.UpdateDiscountDTO;
import accesa.pricecomparatorbe.dtos.UpdatePriceDTO;
import accesa.pricecomparatorbe.services.MarketProductService;
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
    public ResponseEntity<?> getProducts(@RequestParam(defaultValue = "false") boolean withUnit) {
        try {
            System.out.println("Hi");
            if (withUnit) {
                System.out.println("With");
                return ResponseEntity.ok(marketProductService.getProductsWithPricePerUnit());
            } else {
                System.out.println("Without");
                return ResponseEntity.ok(marketProductService.getProducts());
            }
        } catch (Exception e) {
            System.out.println("Die");
            return ResponseEntity.badRequest().body("An error occurred: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
    }

    @GetMapping("/best-discounts")
    public ResponseEntity<?> getBestDiscountsProducts(@RequestBody BestDiscountDTO howManyProducts) {
        try {
            return ResponseEntity.ok(marketProductService.getProductsWithHighestDiscount(howManyProducts.getHowManyProducts()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
    }

    @GetMapping("/new-discounts")
    public ResponseEntity<?> getNewestDiscountedProducts() {
        try {
            return ResponseEntity.ok(marketProductService.getProductsWithLatestDiscounts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<?> updateProductPrice(@PathVariable Long id,
                                                @RequestBody UpdatePriceDTO dto) {
        try {
            marketProductService.updateProductPrice(id, dto);
            return ResponseEntity.ok("Product updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
    }

    @PutMapping("/{id}/discount")
    public ResponseEntity<?> updateProductPrice(@PathVariable Long id,
                                                @RequestBody UpdateDiscountDTO dto) {
        try {
            marketProductService.updateProductDiscount(id, dto);
            return ResponseEntity.ok("Product updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getProductPriceHistory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(marketProductService.getHistoryForProduct(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/histories")
    public ResponseEntity<?> getProductPriceHistory(@RequestParam(defaultValue = "-1") Long retailerId,
                                                    @RequestParam(defaultValue = "-1") Long categoryId,
                                                    @RequestParam(defaultValue = "-1") Long brandId) {
        try {
            return ResponseEntity.ok(marketProductService.getHistories(retailerId, categoryId, brandId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
