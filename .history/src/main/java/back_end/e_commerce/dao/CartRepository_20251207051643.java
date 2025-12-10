package back_end.e_commerce.dao;
import back_end.e_commerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import back_end.e_commerce.model.LocalUser;



public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long cart);
        Cart findByUser(LocalUser user); // ← New method

}