package back_end.e_commerce.model;
import lombok.Data;

    

public class CartItemResponse {
    private Long productId;
    private String name;
    private Double price;
    private String image;
    private int quantity;

    public CartItemResponse(Long productId, String name, Double price, String image, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
