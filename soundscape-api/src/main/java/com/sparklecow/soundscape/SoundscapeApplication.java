package com.sparklecow.soundscape;

import com.sparklecow.soundscape.entities.user.Role;
import com.sparklecow.soundscape.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class SoundscapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoundscapeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			Optional<Role> role = roleRepository.findByRoleName("USER");
			if(role.isEmpty()){
				roleRepository.save(Role.builder().roleName("USER").build());
			}
		};
	}
}
