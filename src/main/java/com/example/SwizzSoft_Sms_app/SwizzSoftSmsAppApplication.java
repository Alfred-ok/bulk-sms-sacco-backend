package com.example.SwizzSoft_Sms_app;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;

@SpringBootApplication
public class SwizzSoftSmsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwizzSoftSmsAppApplication.class, args);
	}



}


