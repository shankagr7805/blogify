package com.shank.Blogify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {

        String url = System.getenv("CLOUDINARY_URL");

        System.out.println("====== CLOUDINARY DEBUG ======");
        System.out.println("URL = " + url);
        System.out.println("==============================");

        if (url == null || url.isBlank()) {
            throw new RuntimeException("CLOUDINARY_URL missing in ENV");
        }

        return new Cloudinary(url);
    }
}