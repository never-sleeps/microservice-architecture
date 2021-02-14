package ru.otus.microservicearchitecture.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication application = new SpringApplication(Application.class);

		Properties properties = new Properties();
		properties.load(new FileInputStream("./config/application.properties"));
		properties.put("spring.datasource.username", System.getenv("dbusername"));
		properties.put("spring.datasource.password", System.getenv("dbpassword"));
		application.setDefaultProperties(properties);

		System.out.println("Properties loaded: " + properties);

		application.run(args);
	}

}
