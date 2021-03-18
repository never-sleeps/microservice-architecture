package ru.microservicearchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) throws IOException  {
		SpringApplication application = new SpringApplication(UserServiceApplication.class);

		Properties properties = new Properties();
		properties.load(new FileInputStream("./config/application.properties"));
		properties.put("spring.datasource.username", System.getenv("dbuser"));
		properties.put("spring.datasource.password", System.getenv("dbpswd"));
		application.setDefaultProperties(properties);

		System.out.println("Properties loaded: " + properties);

		application.run(args);
	}
}
