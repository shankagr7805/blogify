package com.shank.Blogify.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"),
            "api_key", System.getenv("CLOUDINARY_API_KEY"),
            "api_secret", System.getenv("CLOUDINARY_API_SECRET")
        ));
    }

    public static void deleteByUrl(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            System.err.println("Failed to delete Cloudinary image: " + imageUrl);
        }
    }

    private static String extractPublicId(String url) {
        // Example:
        // https://res.cloudinary.com/demo/image/upload/v123/blogify/posts/abc.jpg
        try {
            String[] parts = url.split("/upload/");
            if (parts.length < 2) return null;

            String path = parts[1];
            path = path.replaceAll("v\\d+/", "");
            return path.substring(0, path.lastIndexOf('.'));
        } catch (Exception e) {
            return null;
        }
    }
}