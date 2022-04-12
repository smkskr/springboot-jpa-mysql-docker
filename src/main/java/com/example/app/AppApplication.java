package com.example.app;

import com.example.app.enums.Events;
import com.example.app.enums.SubStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication{

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
