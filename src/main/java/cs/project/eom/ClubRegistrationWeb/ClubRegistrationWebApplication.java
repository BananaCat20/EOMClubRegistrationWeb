package cs.project.eom.ClubRegistrationWeb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClubRegistrationWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubRegistrationWebApplication.class, args);
	}

}
