package br.com.tzmarcio.gerenciamento_projetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GerenciamentoProjetosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoProjetosApplication.class, args);
	}

}
