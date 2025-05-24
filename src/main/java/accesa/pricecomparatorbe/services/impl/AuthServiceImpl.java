package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.dtos.UserDTO;
import accesa.pricecomparatorbe.model.AppUser;
import accesa.pricecomparatorbe.persistence.UserRepository;
import accesa.pricecomparatorbe.services.AuthService;
import accesa.pricecomparatorbe.validators.UserValidator;
import accesa.pricecomparatorbe.validators.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public AuthServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public void registerUser(UserDTO request) throws ValidationException {
        userValidator.validateUserDTO(request);
        AppUser user = AppUser.builder().username(request.getUsername()).build();
        userRepository.save(user);
    }
}
