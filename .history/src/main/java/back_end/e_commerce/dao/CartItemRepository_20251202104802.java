package back_end.e_commerce.dao;
import back_end.e_commerce.model.Cart;
import back_end.e_commerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}