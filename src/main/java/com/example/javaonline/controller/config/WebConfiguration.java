package com.example.javaonline.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${folder.image}")
    String folderImage;

    public static String imageFolderStatic;

    // Nó được chạy sau khi khởi tạo bean xong
    @PostConstruct
    void init(){
        imageFolderStatic = folderImage;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/product-images/**")
                .addResourceLocations("file:/" + folderImage);
    }
}
