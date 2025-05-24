package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.UserDTO;
import accesa.pricecomparatorbe.model.AppUser;
import accesa.pricecomparatorbe.validators.ValidationException;

import java.util.List;

public interface AuthService {
    void registerUser(UserDTO request)  throws ValidationException;
}
