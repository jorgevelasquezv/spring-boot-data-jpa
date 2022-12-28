package co.com.jorge.springboot.app.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        log.info("ResourcePath: " + resourcePath);

        registry.addResourceHandler("/uploads/**")
//                Método de carga externo
//                .addResourceLocations("file:/C:/Temp/uploads/");
                .addResourceLocations(resourcePath);
    }
}
