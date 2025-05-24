package accesa.pricecomparatorbe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import accesa.pricecomparatorbe.model.Currency;
import accesa.pricecomparatorbe.services.CurrencyService;
import accesa.pricecomparatorbe.dtos.CurrencyDTO;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<?> addCurrency(@RequestBody CurrencyDTO currencyDTO) {
        try {
            currencyService.addCurrency(currencyDTO);
            return ResponseEntity.ok("Currency added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCurrencies() {
        try {
            List<Currency> currencies = currencyService.getCurrencies();
            return ResponseEntity.ok(currencies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
