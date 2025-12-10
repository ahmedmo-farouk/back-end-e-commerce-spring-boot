package back_end.e_commerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import back_end.e_commerce.dao.ProductRepository;
import back_end.e_commerce.model.Product;

import java.io.File;
import java.io.IOException;
@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        String uploadDir = "uploads/";
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String imagePath = uploadDir + image.getOriginalFilename();
        image.transferTo(new File(imagePath));

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImagePath(imagePath);
        productRepository.save(product);

        return "Product added successfully!";
    }    }
