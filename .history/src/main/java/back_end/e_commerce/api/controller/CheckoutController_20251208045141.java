package back_end.e_commerce.api.controller;
import back_end.e_commerce.api.model.CheckoutRequest;
import back_end.e_commerce.api.model.ErrorResponse;
import back_end.e_commerce.model.Order;
import back_end.e_commerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> doCheckout(Authentication authentication, @RequestBody CheckoutRequest checkoutRequest) {
        try {
            LocalUser user = (LocalUser) authentication.getPrincipal();
            Order order = checkoutService.checkout(user.getId(), checkoutRequest);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            String reason = e.getMessage();
            ErrorResponse error = new ErrorResponse(
                "Failure in preview",
                reason,
                400
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            String reason = e.getMessage() != null ? e.getMessage() : "Unknown error";
            ErrorResponse error = new ErrorResponse(
                "Failure in preview",
                reason,
                500
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}