package accesa.pricecomparatorbe.services.impl;

import accesa.pricecomparatorbe.model.AppUser;
import accesa.pricecomparatorbe.persistence.UserRepository;
import accesa.pricecomparatorbe.services.UserService;
import accesa.pricecomparatorbe.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }
}
