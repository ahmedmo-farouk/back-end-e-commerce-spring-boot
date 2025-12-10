package back_end.e_commerce.api.controller;
import back_end.e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import back_end.e_commerce.model.CartRequest;
import back_end.e_commerce.model.CartItemResponse;
import back_end.e_commerce.dao.ProductRepository;
import back_end.e_commerce.dao.CartRepository;
import back_end.e_commerce.dao.CartItemRepository;
import back_end.e_commerce.model.Cart;
import back_end.e_commerce.model.CartItem;
import java.util.List;



    

// @RestController
// @RequestMapping("/api/cart")
// @CrossOrigin("*")
// public class CartController {

//     @Autowired
//     private ProductRepository productRepository;

//     @PostMapping
//     public ResponseEntity<CartItemResponse> addToCart(@RequestBody CartRequest request) {

//         Product product = productRepository.findById(request.getProductId())
//                 .orElseThrow(() -> new RuntimeException("Product not found"));

//         CartItemResponse response = new CartItemResponse(
//                 product.getId(),
//                 product.getName(),
//                 product.getPrice(),
//                 product.getImagePath(),
//                 request.getQuantity()
//         );

//         return ResponseEntity.ok(response);
//     }
// }
@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(@RequestBody CartRequest request) {

LocalUser user = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
Long userId = user.getId();
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cartRepository.save(cart);
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        // 4) تجهيز Response
        CartItemResponse response = new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImagePath(),
                request.getQuantity()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getCart() {

        Long userId = 1L; // مؤقت

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return ResponseEntity.ok(List.of());
        }

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemResponse> response = items.stream().map(item ->
                new CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getProduct().getImagePath(),
                        item.getQuantity()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }
}