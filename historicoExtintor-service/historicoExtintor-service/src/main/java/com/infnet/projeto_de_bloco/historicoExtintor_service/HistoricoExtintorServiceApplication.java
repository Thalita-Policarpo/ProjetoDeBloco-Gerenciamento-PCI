package com.infnet.projeto_de_bloco.historicoExtintor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HistoricoExtintorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(HistoricoExtintorServiceApplication.class, args);
	}
}