package com.infnet.projeto_de_bloco.swagger.Aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SwaggerAggregatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(SwaggerAggregatorApplication.class, args);
	}
}