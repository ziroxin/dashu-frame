package com.kg.dashu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kg.**")
public class DashuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashuApplication.class, args);
    }

}
