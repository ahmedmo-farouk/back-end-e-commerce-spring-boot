package back_end.e_commerce.dao;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(LocalUser user);
}