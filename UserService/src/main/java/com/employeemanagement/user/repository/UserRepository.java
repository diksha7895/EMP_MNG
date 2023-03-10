package com.employeemanagement.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeemanagement.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Boolean existsByEmail(String email);

	Boolean existsByUsername(String username);

//	Optional<User> findById(Long id);
//
//	void save(User user);
}
