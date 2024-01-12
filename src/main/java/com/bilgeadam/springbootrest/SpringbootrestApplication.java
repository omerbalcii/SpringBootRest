package com.bilgeadam.springbootrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = MultipartAutoConfiguration.class)
public class SpringbootrestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringbootrestApplication.class, args);
	}
}