package com.antigravity.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "Access is Denied"),

    // Animal
    ANIMAL_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "Animal not found"),
    ANIMAL_NOT_VIEWABLE(HttpStatus.BAD_REQUEST, "A002", "Animal is not in viewable status"),

    // Adoption
    ADOPTION_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "AD001", "Adoption request not found"),
    ADOPTION_REQUEST_INVALID_STATUS(HttpStatus.BAD_REQUEST, "AD002", "Invalid animal status for adoption"),

    // File
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "F001", "Failed to upload file"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F002", "File not found"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "F003", "Invalid file type. Only JPG and PNG are allowed.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
