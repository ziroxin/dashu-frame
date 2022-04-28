package com.kg.dashu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DashuApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DashuApplication.class, args);
        System.out.println(111);
    }

}
