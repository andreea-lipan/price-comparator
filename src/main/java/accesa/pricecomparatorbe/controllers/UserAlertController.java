package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.AlertDTO;
import accesa.pricecomparatorbe.services.UserAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/alert")
public class UserAlertController {

    private final UserAlertService alertService;

    public UserAlertController(UserAlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<?> addAlert(@RequestBody AlertDTO dto) {
        try {
            alertService.createAlert(dto);
            return ResponseEntity.ok("Alert added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
