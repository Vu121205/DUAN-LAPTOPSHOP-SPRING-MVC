package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloController {
    @GetMapping("/")
    public String index()
    {
        return "Hello world from Spring boot";
    }
    
}
