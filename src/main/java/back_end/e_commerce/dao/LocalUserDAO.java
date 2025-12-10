package back_end.e_commerce.dao;
import org.springframework.data.repository.CrudRepository;
import back_end.e_commerce.model.LocalUser;
import java.util.Optional;


public interface LocalUserDAO extends CrudRepository<LocalUser, Long> {
  Optional<LocalUser> findByUsernameIgnoreCase(String username);
  Optional<LocalUser> findByEmailIgnoreCase(String email);
}