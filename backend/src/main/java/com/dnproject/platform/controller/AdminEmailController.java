package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/emails")
@RequiredArgsConstructor
public class AdminEmailController {

    private final EmailService emailService;

    @PostMapping("/send-announcement")
    public ResponseEntity<ApiResponse<Void>> sendAnnouncement(@RequestBody Map<String, String> body) {
        String to = body.get("to");
        String subject = body.get("subject");
        String content = body.get("content");

        emailService.sendEmail(to, subject, content);
        return ResponseEntity.ok(ApiResponse.success("공지 이메일 발송 요청이 완료되었습니다.", null));
    }
}
