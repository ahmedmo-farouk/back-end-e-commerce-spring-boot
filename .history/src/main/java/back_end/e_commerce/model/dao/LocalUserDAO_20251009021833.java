package back_end.e_commerce.model.dao;
import org.springframework.data.repository.CrudRepository;
import back_end.e_commerce.model.LocalUser;
import java.util.Optional;

public interface LocalUserDAO extends CrudRepository<LocalUser, Long> {
  optional<LocalUser> findByUsernameIgnoreCase(String username);
  optional<LocalUser> findByEmailIgnoreCase(String email);
}