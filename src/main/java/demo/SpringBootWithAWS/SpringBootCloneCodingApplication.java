package demo.SpringBootWithAWS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //Jpa Auditing 활성화
@SpringBootApplication
public class SpringBootCloneCodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCloneCodingApplication.class, args);
	}

}
