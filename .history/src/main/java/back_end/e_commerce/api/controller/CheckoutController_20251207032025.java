package back_end.e_commerce.api.controller;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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