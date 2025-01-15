package demo.SpringBootWithAWS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //SpringBootApplication과 분리 후 여기서 활성화
public class JpaConfig {
}
