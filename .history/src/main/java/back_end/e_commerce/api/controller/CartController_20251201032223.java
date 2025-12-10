package back_end.e_commerce.api.controller;

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
