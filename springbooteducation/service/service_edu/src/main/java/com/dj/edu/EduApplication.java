package com.dj.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//添加@ComponentScan(basePackages = {"com.dj"})，使service的启动类也能扫描到common里的swagger配置类
@ComponentScan(basePackages = {"com.dj"})//组件扫描
@SpringBootApplication//启动类
//@EnableSwagger2
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
