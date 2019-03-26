package com.lion;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan(basePackages = {"com.baomidou", "com.lion"})
public class LionStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LionStarterApplication.class, args);
    }

}
