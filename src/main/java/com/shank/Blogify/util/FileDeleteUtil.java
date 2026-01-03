package com.shank.Blogify.util;

import java.nio.file.*;

public class FileDeleteUtil {

    public static void deleteUploadFile(String imageUrl) {
        try {
            if (!imageUrl.startsWith("/uploads/")) return;

            String filename = imageUrl.replace("/uploads/", "");
            Path path = Paths.get("src/main/resources/static/uploads", filename);

            Files.deleteIfExists(path);
        } catch (Exception e) {
            System.err.println("Failed to delete image: " + imageUrl);
        }
    }
}