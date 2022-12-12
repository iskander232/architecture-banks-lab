package edu.phystech.banks_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BanksLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanksLabApplication.class, args);
	}

}
