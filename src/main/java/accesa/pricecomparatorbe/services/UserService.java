package accesa.pricecomparatorbe.services;

import accesa.pricecomparatorbe.model.AppUser;
import java.util.List;

public interface UserService {
    List<AppUser> getUsers();

    AppUser getUserById(Long userId);
}
