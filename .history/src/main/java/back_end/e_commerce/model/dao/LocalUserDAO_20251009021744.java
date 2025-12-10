package back_end.e_commerce.model.dao;

public interface LocalUserDAO extends CrudRepository<LocalUser, Long> {
  optional<LocalUser> findByUsernameIgoreCase(String username);
  optional<LocalUser> findByEmailIgoreCase(String email);
}