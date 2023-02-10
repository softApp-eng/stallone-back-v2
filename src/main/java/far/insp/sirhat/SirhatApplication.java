package far.insp.sirhat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootApplication
public class SirhatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SirhatApplication.class, args);
	}

	@Value("${stallone.images}")
	private String articleFolder;
	@Bean
	CommandLineRunner init(){
		return args -> {
			Files.createDirectories(Paths.get(articleFolder));
			System.err.println(UUID.randomUUID().toString());
			System.err.println(new BCryptPasswordEncoder().encode("123456"));
		 };
	}
}
