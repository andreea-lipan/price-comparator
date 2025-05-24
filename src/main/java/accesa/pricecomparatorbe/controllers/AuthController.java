package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.UserDTO;
import accesa.pricecomparatorbe.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerCompany(@RequestBody UserDTO userDTO) {
        try {
            authService.registerUser(userDTO);
            return ResponseEntity.ok("Account created successfully!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Username already exists.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
