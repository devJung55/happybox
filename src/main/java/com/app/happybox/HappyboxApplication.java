package com.app.happybox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HappyboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyboxApplication.class, args);
    }

}
