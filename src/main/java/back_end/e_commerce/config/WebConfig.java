package back_end.e_commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ المسار المطلق للـ uploads
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        
        System.out.println("✅ Upload directory: " + uploadDir); // للتأكد من المسار
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir);
    }
}