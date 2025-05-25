package accesa.pricecomparatorbe.controllers;

import accesa.pricecomparatorbe.dtos.BasketDTO;
import accesa.pricecomparatorbe.dtos.UserTokenDTO;
import accesa.pricecomparatorbe.model.Basket;
import accesa.pricecomparatorbe.services.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public ResponseEntity<?> addBasket(@RequestBody BasketDTO basketDTO) {
        try {
            System.out.println(basketDTO);
            basketService.addToBasket(basketDTO);
            return ResponseEntity.ok("Basket added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // In a real app we wouldn't need to use a UserTokenDTO
    // we could identify the user based on their auth token
    // in a real app with Spring and Token Authentication the data
    // that arrives here would be valid bc Spring handles the token validation
    // so we will assume UserTokenDTO valid
    @GetMapping
    public ResponseEntity<?> getUserBasket(@RequestBody UserTokenDTO userTokenDTO) {
        try {
            Basket basket = basketService.getBasketByUserId(userTokenDTO.getUserId());
            return ResponseEntity.ok(basket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/shopping-lists")
    public ResponseEntity<?> getShoppingLists(@RequestBody UserTokenDTO userTokenDTO) {
        try {
            System.out.println("Right requests");
            return ResponseEntity.ok(basketService.getShoppingLists(userTokenDTO.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
