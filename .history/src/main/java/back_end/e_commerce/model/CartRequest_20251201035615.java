package back_end.e_commerce.model;
import lombok.Data;

@Data
public class CartRequest {
    private Long productId;
    private int quantity;
}