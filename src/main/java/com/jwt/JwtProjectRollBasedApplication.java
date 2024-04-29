package com.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jwt.entity.Role;
import com.jwt.entity.User;
import com.jwt.repository.UserRepo;

@SpringBootApplication
public class JwtProjectRollBasedApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(JwtProjectRollBasedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User adminAccount = userRepo.findByRole(Role.ADMIN);
		
		if(adminAccount ==null) {
			User user = new User();
			
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setSecondname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepo.save(user);
		}
		
	}

}
