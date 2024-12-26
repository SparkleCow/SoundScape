package com.sparklecow.soundscape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoundscapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoundscapeApplication.class, args);
	}

}
