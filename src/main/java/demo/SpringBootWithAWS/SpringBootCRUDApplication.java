package demo.SpringBootWithAWS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing //Jpa Auditing 활성화
//SpringBootApplication과 분리
@SpringBootApplication
public class SpringBootCRUDApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCRUDApplication.class, args);
	}
}
