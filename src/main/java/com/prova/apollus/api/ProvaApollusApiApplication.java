package com.prova.apollus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.prova.apollus.api.config.property.ProvaApollusApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ProvaApollusApiProperty.class)
public class ProvaApollusApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvaApollusApiApplication.class, args);
	}

}
