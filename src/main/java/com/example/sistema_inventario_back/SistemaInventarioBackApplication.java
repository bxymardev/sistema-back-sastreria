package com.example.sistema_inventario_back;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SistemaInventarioBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(SistemaInventarioBackApplication.class, args);

	}

	@PostConstruct
	public void init() {
		// Configurar zona horaria de Bolivia (UTC-4)
		TimeZone.setDefault(TimeZone.getTimeZone("America/Caracas"));
	}
}