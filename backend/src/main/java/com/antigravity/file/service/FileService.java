package com.antigravity.file.service;

import com.antigravity.global.exception.BusinessException;
import com.antigravity.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${animal.upload-path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("파일이 비어있습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);

        if (!isSupportedExtension(extension)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_TYPE);
        }

        String savedFilename = UUID.randomUUID().toString() + "." + extension;
        File targetFile = new File(uploadPath, savedFilename);

        // 디렉토리 생성
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(targetFile);
            return savedFilename;
        } catch (IOException e) {
            log.error("File upload error", e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    public void deleteFile(String filename) {
        File file = new File(uploadPath, filename);
        if (file.exists()) {
            file.delete();
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isSupportedExtension(String extension) {
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }
}
