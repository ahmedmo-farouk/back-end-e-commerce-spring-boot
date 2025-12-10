package back_end.e_commerce.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import back_end.e_commerce.model.Product; 
import java.util.List;
    



public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByBrandIgnoreCase(String brand);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);
}