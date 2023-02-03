package far.insp.sirhat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class SirhatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SirhatApplication.class, args);
	}

	@Bean
	CommandLineRunner init(){
		return args -> {
			System.err.println(UUID.randomUUID().toString());
			System.err.println(new BCryptPasswordEncoder().encode("123456"));
		};
	}
}
