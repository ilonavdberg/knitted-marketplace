package com.knitted.marketplace.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public class FileUtils {
    public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return getFileExtension(originalFileName);
    }

    public static String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }

    public static String generateUniqueFileName(MultipartFile file) {
        String extension = getFileExtension(file);
        return "IMG" + UUID.randomUUID().toString().substring(23) + extension;
    }
}
