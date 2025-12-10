package back_end.e_commerce.model.dao;

public interface LocalUserDAO extends CrudRepository<LocalUser, Long> {
  optional<LocalUser> findByUsernameIgnoreCase(String username);
  optional<LocalUser> findByEmailIgnoreCase(String email);
}