package weprosever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "weprosever")
@EnableCaching
public class WeproseverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeproseverApplication.class, args);
	}
}
