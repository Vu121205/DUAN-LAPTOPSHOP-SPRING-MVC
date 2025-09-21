package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LaptopshopApplication {

	public static void main(String[] args) {

		

		ApplicationContext vudoan = SpringApplication.run(LaptopshopApplication.class, args);

		for(String s: vudoan.getBeanDefinitionNames())
		{
			System.out.println(s);
		}
	}

}
