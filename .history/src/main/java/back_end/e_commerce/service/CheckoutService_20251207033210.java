package back_end.e_commerce.service;
import back_end.e_commerce.dao.CartItemRepository;
import back_end.e_commerce.dao.CartRepository;
import back_end.e_commerce.dao.OrderItemRepository;
import back_end.e_commerce.dao.OrderRepository;
import back_end.e_commerce.model.Cart;
import back_end.e_commerce.model.CartItem;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CheckoutService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository itemRepo;

    public Order checkout() {

        Cart cart = cartService.getUserCart();

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalPrice(cart.getItems()
                .stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum());

        order = orderRepo.save(order);

        // نقل بيانات الكارت الى order_items
        for (CartItem item : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(item.getProduct());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getProduct().getPrice());

            orderItemRepo.save(oi);
        }

        // حذف cart items
        itemRepo.deleteAll(cart.getItems());

        return order;
    }
}
