package com.thesis.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class BackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(BackendApplication.class, args);
	}

}
