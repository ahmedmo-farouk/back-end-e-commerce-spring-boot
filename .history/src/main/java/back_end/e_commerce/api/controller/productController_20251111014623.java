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

    // ✅ Method مساعد لحفظ الصورة
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
        
        return "/uploads/" + fileName; // ✅ مسار نسبي
    }

 

    // ✅ إضافة منتج مع صورة
  @PostMapping("/add")
public ResponseEntity<?> addProduct(@RequestBody Product product) {
    Product savedProduct = productRepository.save(product);
    return ResponseEntity.ok(savedProduct); // ✅ ارجع المنتج مع الـ ID
}

// 2. رفع الصورة للمنتج
@PostMapping("/{id}/upload-image")
public ResponseEntity<?> uploadProductImage(
        @PathVariable Long id,
        @RequestParam("image") MultipartFile image) {
    
    try {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String imagePath = saveImage(image);
        product.setImagePath(imagePath);
        productRepository.save(product);

        return ResponseEntity.ok("Image uploaded successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
    }
}
    @GetMapping("/all")
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

            // ✅ حذف الصورة من الـ file system
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

            // ✅ لو في صورة جديدة
            if (image != null && !image.isEmpty()) {
                // احذف الصورة القديمة
                if (product.getImagePath() != null) {
                    String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
                    String oldFileName = product.getImagePath().replace("/uploads/", "");
                    File oldFile = new File(uploadDir + oldFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
                
                // احفظ الصورة الجديدة
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