package com.loaneligibility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class LoanEligibilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanEligibilityApplication.class, args);
	}
	public void addCorsMappings(CorsRegistry registry) {
        // Allows all origins to access the API without explicitly specifying the localhost
        registry.addMapping("/**")
                .allowedOrigins("*")  // Allow any origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow all methods
                .allowedHeaders("*");  // Allow all headers
    }

}
