package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SeataOrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication.class, args);
    }

}
