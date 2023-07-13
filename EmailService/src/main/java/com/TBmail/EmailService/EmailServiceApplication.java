package com.TBmail.EmailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class EmailServiceApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(EmailServiceApplication.class, args);
		
		
		
	}
	
	//@Scheduled(cron = "0 * * * * *") 
	public void getRequest() {
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.getForObject("http://localhost:8080/tb/mail", String.class);
		
	}

}