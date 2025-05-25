package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.RetailerDTO;
import accesa.pricecomparatorbe.model.Retailer;
import accesa.pricecomparatorbe.services.RetailerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/retailer")
public class RetailerController {

    private final RetailerService retailerService;

    public RetailerController(RetailerService retailerService) {
        this.retailerService = retailerService;
    }

    @PostMapping
    public ResponseEntity<?> addRetailer(@RequestBody RetailerDTO retailerDTO) {
        try {
            retailerService.addRetailer(retailerDTO);
            return ResponseEntity.ok("Retailer added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getRetailers() {
        try {
            List<Retailer> retailers = retailerService.getRetailers();
            return ResponseEntity.ok(retailers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
