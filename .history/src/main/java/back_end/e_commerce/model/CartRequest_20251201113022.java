package back_end.e_commerce.model;
import lombok.Data;
    
@Data
public class CartRequest {
    private Long productId;
    private int quantity;
}


    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
