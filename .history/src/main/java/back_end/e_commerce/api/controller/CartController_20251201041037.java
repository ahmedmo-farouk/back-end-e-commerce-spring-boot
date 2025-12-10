package back_end.e_commerce.api.controller;
import back_end.e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import back_end.e_commerce.model.CartRequest;
import back_end.e_commerce.model.CartItemResponse;
import back_end.e_commerce.dao.ProductRepository;


    

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(@RequestBody CartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemResponse response = new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImagePath(),
                request.getQuantity()
        );

        return ResponseEntity.ok(response);
    }
}
