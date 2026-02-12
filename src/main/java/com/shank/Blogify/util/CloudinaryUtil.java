package com.shank.Blogify.util;

import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryUtil {

    private final Cloudinary cloudinary;

    // 🔥 Spring will inject Cloudinary bean automatically
    public CloudinaryUtil(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // ✅ Delete image by URL
    public void deleteByUrl(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);

            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                System.out.println("Deleted Cloudinary image: " + publicId);
            }

        } catch (Exception e) {
            System.err.println("Failed to delete Cloudinary image: " + imageUrl);
            e.printStackTrace();
        }
    }

    // ✅ Extract Cloudinary publicId from URL
    private String extractPublicId(String url) {
        try {
            // Example:
            // https://res.cloudinary.com/demo/image/upload/v123/blogify/posts/abc.jpg

            String[] parts = url.split("/upload/");
            if (parts.length < 2) return null;

            String path = parts[1];

            // remove version folder (v123/)
            path = path.replaceAll("v\\d+/", "");

            // remove extension (.jpg/.png)
            return path.substring(0, path.lastIndexOf('.'));

        } catch (Exception e) {
            return null;
        }
    }
}