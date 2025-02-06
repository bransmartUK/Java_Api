package bran.bran_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("bran.bran_backend.models")
@EnableJpaRepositories("bran.bran_backend.repositories")
public class BranBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BranBackendApplication.class, args);
	}

}
