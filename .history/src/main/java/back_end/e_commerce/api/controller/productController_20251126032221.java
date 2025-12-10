package back_end.e_commerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import back_end.e_commerce.dao.ProductRepository;
import back_end.e_commerce.model.Product;
import java.io.File;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private ProductRepository productRepository;

   private String saveImage(MultipartFile image) throws Exception {
    if (image == null || image.isEmpty()) {
        return null;
    }
    
    String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
    File directory = new File(uploadDir);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
    File file = new File(uploadDir + fileName);
    image.transferTo(file);
    
    System.out.println("✅ Image saved at: " + file.getAbsolutePath()); 
    System.out.println("✅ Image path returned: /uploads/" + fileName); 
    
    return "/uploads/" + fileName;
}

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {
        
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            String imagePath = saveImage(image);
            product.setImagePath(imagePath);
            Product updatedProduct = productRepository.save(product);

            return ResponseEntity.ok(updatedProduct); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

   @GetMapping("/all")
public ResponseEntity<?> getAllProducts(
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String category) {

    try {
        if (brand != null) {
            return ResponseEntity.ok(productRepository.findByBrandIgnoreCase(brand));
        }

        if (category != null) {
            return ResponseEntity.ok(productRepository.findByCategoryIgnoreCase(category));
        }

        return ResponseEntity.ok(productRepository.findAll());

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

            if (product.getImagePath() != null) {
                String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
                String fileName = product.getImagePath().replace("/uploads/", "");
                File imageFile = new File(uploadDir + fileName);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }

            productRepository.delete(product);
            return ResponseEntity.ok("Product deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
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
                if (product.getImagePath() != null) {
                    String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
                    String oldFileName = product.getImagePath().replace("/uploads/", "");
                    File oldFile = new File(uploadDir + oldFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
                
                String newImagePath = saveImage(image);
                product.setImagePath(newImagePath);
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