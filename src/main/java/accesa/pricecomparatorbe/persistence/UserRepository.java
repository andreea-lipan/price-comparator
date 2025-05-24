package accesa.pricecomparatorbe.persistence;

import accesa.pricecomparatorbe.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

}
