package back_end.e_commerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import back_end.e_commerce.model.LocalUser;

public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
}