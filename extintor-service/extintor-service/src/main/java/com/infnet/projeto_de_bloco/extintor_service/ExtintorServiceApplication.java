package com.infnet.projeto_de_bloco.extintor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExtintorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExtintorServiceApplication.class, args);
	}
}