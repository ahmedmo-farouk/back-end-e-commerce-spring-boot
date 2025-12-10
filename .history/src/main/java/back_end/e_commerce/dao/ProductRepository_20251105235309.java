package back_end.e_commerce.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import back_end.e_commerce.model.Product; 



public interface ProductRepository extends JpaRepository<Product, Long> {
}