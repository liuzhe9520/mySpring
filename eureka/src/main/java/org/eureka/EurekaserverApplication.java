package org.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaServer
@SpringBootApplication
public class EurekaserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverApplication.class, args);
    }
    
    @Value("${my.name}")
    private String name;
    
    @Value("${my.age}")
    private int age;
    
    @GetMapping("hi")
    public String hi(){
    	System.out.println("name:" + name + ", age:" + age);
    	return "hello world";
    }
}