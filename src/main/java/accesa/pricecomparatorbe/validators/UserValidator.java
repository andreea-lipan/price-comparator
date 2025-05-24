package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    public void validateUserDTO(UserDTO userDTO) throws ValidationException {
        if (userDTO == null) {
            throw new ValidationException("User data must not be null!");
        } else if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required!");
        }
    }
}
