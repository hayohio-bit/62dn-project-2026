package com.dnproject.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${animal.upload-path:uploads}")
    private String uploadPath;

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;
            Path targetPath = root.resolve(fileName);

            Files.copy(file.getInputStream(), targetPath);

            log.info("File stored: {}", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Could not store file", e);
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path file = Paths.get(uploadPath).resolve(fileName);
            Files.deleteIfExists(file);
            log.info("File deleted: {}", fileName);
        } catch (IOException e) {
            log.error("Could not delete file: {}", fileName, e);
        }
    }

    public Path loadFile(String fileName) {
        return Paths.get(uploadPath).resolve(fileName);
    }
}
