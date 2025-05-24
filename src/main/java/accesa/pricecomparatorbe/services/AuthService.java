package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.dtos.UserDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

public interface AuthService {
    void registerUser(UserDTO request)  throws ValidationException;
}
