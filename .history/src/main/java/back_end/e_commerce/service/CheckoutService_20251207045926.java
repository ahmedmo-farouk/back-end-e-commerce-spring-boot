// package back_end.e_commerce.service;
// import back_end.e_commerce.dao.CartItemRepository;
// import back_end.e_commerce.dao.CartRepository;
// import back_end.e_commerce.dao.LocalUserRepository;
// import back_end.e_commerce.dao.OrderItemRepository;
// import back_end.e_commerce.dao.OrderRepository;
// import back_end.e_commerce.dao.ProductRepository;
// import back_end.e_commerce.api.model.CheckoutRequest;
// import back_end.e_commerce.api.model.CheckoutItemRequest;
// import back_end.e_commerce.model.Cart;
// import back_end.e_commerce.model.CartItem;
// import back_end.e_commerce.model.LocalUser;
// import back_end.e_commerce.model.Order;
// import back_end.e_commerce.model.OrderItem;
// import back_end.e_commerce.model.Product;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;




// @Service
// public class CheckoutService {

//     @Autowired
//     private CartService cartService;

//     @Autowired
//     private OrderRepository orderRepo;

//     @Autowired
//     private OrderItemRepository orderItemRepo;

//     @Autowired
//     private CartRepository cartRepo;

//     @Autowired
//     private CartItemRepository itemRepo;

//     @Autowired
//     private LocalUserRepository userRepo;

//     @Autowired
//     private ProductRepository productRepo;

//     public Order checkout(Long userId, CheckoutRequest checkoutRequest) {

//         if (checkoutRequest == null || checkoutRequest.getItems() == null || checkoutRequest.getItems().isEmpty()) {
//             throw new RuntimeException("No items provided for checkout!");
//         }

//         LocalUser user = userRepo.findById(userId)
//                 .orElseThrow(() -> new RuntimeException("User not found!"));

//         Order order = new Order();
//         order.setUser(user);
//         order.setTotalPrice(checkoutRequest.getTotalPrice());

//         order = orderRepo.save(order);

//         // Create order items from the checkout request
//         for (CheckoutItemRequest item : checkoutRequest.getItems()) {
//             Product product = productRepo.findById(item.getProductId())
//                     .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

//             OrderItem oi = new OrderItem();
//             oi.setOrder(order);
//             oi.setProductId(product.getId());
//             oi.setName(product.getName());
//             oi.setQuantity(item.getQuantity());
//             oi.setPrice(product.getPrice());

//             orderItemRepo.save(oi);
//         }

//         return order;
//     }
// }
