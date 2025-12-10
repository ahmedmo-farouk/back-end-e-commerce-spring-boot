package back_end.e_commerce.api.controller;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.service.CheckoutService;
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
   @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public Order doCheckout() {
        return checkoutService.checkout();
    }
}