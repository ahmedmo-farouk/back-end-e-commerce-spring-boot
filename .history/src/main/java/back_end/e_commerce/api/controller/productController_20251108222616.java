package back_end.e_commerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import back_end.e_commerce.dao.ProductRepository;
import back_end.e_commerce.model.Product;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private ProductRepository productRepository;

   @PostMapping("/add")
public ResponseEntity<?> addProduct(
        @RequestParam("name") String name,
        @RequestParam("price") Double price,
        @RequestParam("quantity") Long quantity,
        @RequestParam(value = "image", required = false) MultipartFile image) {

    try {
        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // إن
        }

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(uploadDir + fileName);
            image.transferTo(file);
            imagePath = file.getAbsolutePath();
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setImagePath(imagePath);

        // هنا احفظ المنتج في الـ repository
        // productRepository.save(product);

        return ResponseEntity.ok("Product added successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}

}