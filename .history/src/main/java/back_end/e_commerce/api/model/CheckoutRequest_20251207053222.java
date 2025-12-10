package back_end.e_commerce.api.model;

import java.util.List;

public class CheckoutRequest {
    private Double totalPrice;
    private List<CheckoutItemRequest> items;
    private String address;
    private String paymentMethod;

    public CheckoutRequest() {}

    public CheckoutRequest(Double totalPrice, List<CheckoutItemRequest> items, String address, String paymentMethod) {
        this.totalPrice = totalPrice;
        this.items = items;
        this.address = address;
        this.paymentMethod = paymentMethod;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
