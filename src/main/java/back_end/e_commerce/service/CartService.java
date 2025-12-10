package back_end.e_commerce.service;
import back_end.e_commerce.model.Cart;      
import back_end.e_commerce.dao.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
  
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCurrentUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
