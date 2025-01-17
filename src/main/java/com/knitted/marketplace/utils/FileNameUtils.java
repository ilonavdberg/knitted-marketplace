package com.knitted.marketplace.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileNameUtils {
    public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null && originalFileName.contains(".")) {
            return originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return "";
    }

    public static String generateUniqueFileName(MultipartFile file) {
        String extension = getFileExtension(file);
        return "IMG" + UUID.randomUUID().toString().substring(23) + extension;
    }
}
