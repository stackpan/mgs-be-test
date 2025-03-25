package io.github.stackpan.mgs_be_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "io.github.stackpan.mgs_be_test.config.properties")
public class MgsBeTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgsBeTestApplication.class, args);
	}

}
