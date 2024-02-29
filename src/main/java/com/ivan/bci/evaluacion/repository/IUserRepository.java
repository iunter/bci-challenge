package com.ivan.bci.evaluacion.repository;

import com.ivan.bci.evaluacion.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<UserModel, Integer>
{
	@Query(value = "SELECT u from users u WHERE u.email = ?1")
	public UserModel findByEmail(String email);

}
