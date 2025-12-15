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
import org.springframework.security.core.context.SecurityContextHolder;
import back_end.e_commerce.model.LocalUser;

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
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // If the same product already exists in the cart, increase quantity
        CartItem existing = cartItemRepository.findByCartAndProductId(cart, product.getId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
            cartItemRepository.save(existing);

            CartItemResponse response = new CartItemResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImagePath(),
                    existing.getQuantity()
            );

            return ResponseEntity.ok(response);
        }

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        item = cartItemRepository.save(item);

        CartItemResponse response = new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImagePath(),
                item.getQuantity()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {

        LocalUser user = (LocalUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = user.getId();

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return ResponseEntity.badRequest().body("Cart not found");
        }

        CartItem item = cartItemRepository.findByCartAndProductId(cart, productId);
        if (item == null) {
            return ResponseEntity.badRequest().body("Item not found in cart");
        }

        cartItemRepository.delete(item);

        return ResponseEntity.ok("Item removed successfully");
    }

    @GetMapping
    public ResponseEntity<?> getCart() {

        LocalUser user = (LocalUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = user.getId();

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return ResponseEntity.ok(List.of());
        }

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemResponse> response = items.stream().map(item
                -> new CartItemResponse(
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
