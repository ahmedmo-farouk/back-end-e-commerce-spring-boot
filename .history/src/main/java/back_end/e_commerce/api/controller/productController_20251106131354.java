package back_end.e_commerce.api.controller;

public class productController {
    public productController() {
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        // تحديد مكان تخزين الصورة
        String uploadDir = "uploads/";
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        // حفظ الصورة في المجلد
        String imagePath = uploadDir + image.getOriginalFilename();
        image.transferTo(new File(imagePath));

        // حفظ بيانات المنتج في قاعدة البيانات
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImagePath(imagePath);
        productRepository.save(product);

        return "Product added successfully!";
    }    }
}