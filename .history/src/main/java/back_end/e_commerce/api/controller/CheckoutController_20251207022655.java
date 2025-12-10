package back_end.e_commerce.api.controller;

public class CheckoutController {
   @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public Order doCheckout() {
        return checkoutService.checkout();
    }
}