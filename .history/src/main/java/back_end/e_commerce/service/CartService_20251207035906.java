package back_end.e_commerce.service;
import back_end.e_commerce.model.Cart;      
import back_end.e_commerce.repository.CartRepository;
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
@Entity
public class Cart {
    // ... other fields ...

    @ManyToOne
    private LocalUser user;  // Field is LocalUser type

    // But the getter/setter use User type:
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
