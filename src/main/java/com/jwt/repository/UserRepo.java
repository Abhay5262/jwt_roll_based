package com.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.entity.User;
import com.jwt.entity.Role;

public interface UserRepo extends JpaRepository<User, Long>{
	
	
	Optional<User> findByEmail(String email);
	
	User findByRole(Role role);
}
