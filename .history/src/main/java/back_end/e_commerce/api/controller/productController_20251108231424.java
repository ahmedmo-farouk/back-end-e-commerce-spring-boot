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
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(uploadDir + fileName);
            image.transferTo(file);
            imagePath = "/uploads/" + fileName; // مسار نسبي للعرض
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setImagePath(imagePath);

        productRepository.save(product);

        return ResponseEntity.ok("Product added successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}
@GetMapping("/")
public ResponseEntity<?> getAllProducts() {
    try {
        var products = productRepository.findAll();
        return ResponseEntity.ok(products);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}
@GetMapping("/{id}")
public ResponseEntity<?> getProductById(@PathVariable Long id) {
    try {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ResponseEntity.ok(product);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
    try {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // حذف الصورة من الملفات
        if (product.getImagePath() != null) {
            File imageFile = new File(product.getImagePath());
            if (imageFile.exists()) imageFile.delete();
        }

        productRepository.delete(product);

        return ResponseEntity.ok("Product deleted successfully!");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}

@PutMapping("produt/{id}")
public ResponseEntity<?> editProduct(
        @PathVariable Long id,
        @RequestParam("name") String name,
        @RequestParam("price") Double price,
        @RequestParam("quantity") Long quantity,
        @RequestParam(value = "image", required = false) MultipartFile image) {

    try {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        if (image != null && !image.isEmpty()) {
            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(uploadDir + fileName);
            image.transferTo(file);

            if (product.getImagePath() != null) {
                File oldFile = new File(product.getImagePath());
                if (oldFile.exists()) oldFile.delete();
            }

            product.setImagePath(file.getAbsolutePath());
        }

        productRepository.save(product);

        return ResponseEntity.ok("Product updated successfully!");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}

}
