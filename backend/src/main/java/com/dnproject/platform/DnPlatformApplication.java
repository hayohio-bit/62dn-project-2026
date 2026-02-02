package com.dnproject.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DnPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnPlatformApplication.class, args);
    }

}
