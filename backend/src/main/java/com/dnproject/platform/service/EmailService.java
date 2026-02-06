package com.dnproject.platform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${resend.api-key}")
    private String resendApiKey;

    @Value("${resend.from-email}")
    private String fromEmail;

    @Value("${resend.from-name:DN Platform}")
    private String fromName;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendEmail(String to, String subject, String content) {
        if (resendApiKey == null || resendApiKey.isEmpty() || resendApiKey.contains("YOUR_API_KEY")) {
            log.warn("Resend API key is not configured. Email to {} with subject [{}] was not sent.", to, subject);
            log.debug("Content: {}", content);
            return;
        }

        String url = "https://api.resend.com/emails";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("from", String.format("%s <%s>", fromName, fromEmail));
        body.put("to", to);
        body.put("subject", subject);
        body.put("html", content);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
            log.info("Email successfully sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}
