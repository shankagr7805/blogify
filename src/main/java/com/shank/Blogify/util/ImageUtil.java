package com.shank.Blogify.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtil {

    // Extract all image URLs from HTML body
    public static List<String> extractImageUrls(String html) {
        List<String> urls = new ArrayList<>();

        if (html == null) return urls;

        Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            urls.add(matcher.group(1));
        }

        return urls;
    }

    // Convert Cloudinary URL → public_id
    public static String extractPublicId(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("/upload/")) {
            return null;
        }

        // Example:
        // https://res.cloudinary.com/demo/image/upload/v123/blogify/posts/abc.jpg
        // public_id = blogify/posts/abc

        String afterUpload = imageUrl.split("/upload/")[1];
        afterUpload = afterUpload.replaceAll("v\\d+/", "");
        return afterUpload.substring(0, afterUpload.lastIndexOf('.'));
    }
}