package com.antigravity.file.controller;

import com.antigravity.animal.service.AnimalService;
import com.antigravity.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag(name = "File", description = "File Management API")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final AnimalService animalService;

    @Value("${animal.upload-path}")
    private String uploadPath;

    @Operation(summary = "동물 이미지 업로드")
    @PostMapping("/upload/animal/{animalId}")
    public ResponseEntity<String> uploadAnimalImage(@PathVariable Long animalId,
            @RequestParam("file") MultipartFile file) {
        String savedFilename = fileService.uploadFile(file);
        // 동물 정보에 이미지 경로 업데이트
        animalService.updateAnimalImage(animalId, "/api/files/display/" + savedFilename);
        return ResponseEntity.ok(savedFilename);
    }

    @Operation(summary = "이미지 조회")
    @GetMapping("/display/{filename}")
    public ResponseEntity<Resource> display(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "image/jpeg"; // 기본값
                if (filename.endsWith(".png"))
                    contentType = "image/png";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
