package back_end.e_commerce.model;
    

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

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }
}
