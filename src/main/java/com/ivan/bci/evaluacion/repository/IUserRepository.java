package com.ivan.bci.evaluacion.repository;

import com.ivan.bci.evaluacion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Integer>
{
	@Query(value = "SELECT u from users u WHERE u.email = ?1")
	public User findByEmail(String email);

}
