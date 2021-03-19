package com.prodemo01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.prodemo01.dao")
@SpringBootApplication(scanBasePackages = {"com.prodemo01"})
public class Prodemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(Prodemo01Application.class, args);
    }

}
