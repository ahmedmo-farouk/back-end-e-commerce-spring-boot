package back_end.e_commerce.api.model;

import java.util.List;

public class CheckoutRequest {
    private Double totalPrice;
    private List<CheckoutItemRequest> items;

    public CheckoutRequest() {}

    public CheckoutRequest(Double totalPrice, List<CheckoutItemRequest> items) {
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CheckoutItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemRequest> items) {
        this.items = items;
    }
}
