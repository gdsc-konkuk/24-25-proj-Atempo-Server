package juton113.Atempo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AtempoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtempoApplication.class, args);
	}

}
