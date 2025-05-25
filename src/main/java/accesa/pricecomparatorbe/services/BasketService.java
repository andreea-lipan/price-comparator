package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.BasketDTO;
import accesa.pricecomparatorbe.model.*;
import accesa.pricecomparatorbe.validators.ValidationException;

import java.util.List;
import java.util.Map;

public interface BasketService {

    Basket getBasketByUserId(Long userId);

    Basket getBasketByUser(AppUser user);

    void addToBasket(BasketDTO basketDTO) throws ValidationException;

    Map<String, List<MarketProduct>> getShoppingLists(Long userId);
}
