package accesa.pricecomparatorbe.validators;

import accesa.pricecomparatorbe.dtos.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateUserDTO(UserDTO userDTO) throws ValidationException {
        String errorMessage = "";

        if (userDTO == null) {
            errorMessage += "User data must not be null!\n";
        } else {
            if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
                errorMessage += "Username is required!\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }
}
