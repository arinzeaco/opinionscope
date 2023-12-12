package com.auth.opinionscope.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    private static final String UPLOAD_DIR = "/Users/mac/Desktop/Java Project/opinion-scope/src/main/resources/profile_images"; // Set the desired path for storing profile images
    private static final String BASE_URL = "http://localhost:8080/profile_images/"; // Set the base URL for accessing profile images


    public String uploadProfileImage(MultipartFile imageFile, String userId, String email) throws IOException {
        String extension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        String fileName = generateFileName(userId, email, extension);
        Path filePath = Path.of(UPLOAD_DIR, fileName);

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = BASE_URL + fileName; // Construct the full image URL
        return imageUrl;
    }

    private String generateFileName(String userId, String email, String extension) {
        String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "");
        return userId + "_" + sanitizedEmail + "." + extension;
    }

}