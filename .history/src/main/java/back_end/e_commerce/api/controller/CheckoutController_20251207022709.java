package back_end.e_commerce.api.controller;
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