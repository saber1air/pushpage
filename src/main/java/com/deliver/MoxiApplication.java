package com.deliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoxiApplication.class, args);
	}
}
