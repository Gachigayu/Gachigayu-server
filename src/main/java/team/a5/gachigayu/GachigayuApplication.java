package team.a5.gachigayu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GachigayuApplication {

	public static void main(String[] args) {
		SpringApplication.run(GachigayuApplication.class, args);
	}

}
