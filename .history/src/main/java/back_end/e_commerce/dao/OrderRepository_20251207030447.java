package back_end.e_commerce.dao;

public interface OrderRepository {
    List<Order> findByUser(LocalUser user);

}