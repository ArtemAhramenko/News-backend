package com.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsApplication {

    public static void main(String[] args) {
        System.out.println("test");
        SpringApplication.run(NewsApplication.class, args);

    }
}
