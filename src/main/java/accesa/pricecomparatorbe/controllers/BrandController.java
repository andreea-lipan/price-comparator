package accesa.pricecomparatorbe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.services.BrandService;
import accesa.pricecomparatorbe.dtos.BrandDTO;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    
    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO) {
        try {
            brandService.addBrand(brandDTO);
            return ResponseEntity.ok("Brand added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getBrands() {
        try {
            List<Brand> brands = brandService.getBrands();
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
