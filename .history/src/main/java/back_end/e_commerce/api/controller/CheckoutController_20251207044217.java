package back_end.e_commerce.api.controller;
import back_end.e_commerce.api.model.CheckoutRequest;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import back_end.e_commerce.model.LocalUser;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
   @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public Order doCheckout(Authentication authentication, @RequestBody CheckoutRequest checkoutRequest) {
        LocalUser user = (LocalUser) authentication.getPrincipal();
        return checkoutService.checkout(user.getId(), checkoutRequest);
    }
}