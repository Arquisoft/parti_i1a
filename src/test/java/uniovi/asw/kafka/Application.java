package uniovi.asw.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Exclude hello application
@EntityScan(basePackages = "uniovi")
@EnableJpaRepositories(basePackages = "uniovi")
@ComponentScan(basePackages = { "uniovi.asw.persistence",
		"uniovi.asw.producers", "uniovi.asw.hello.listeners",
		"uniovi.asw.services" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}