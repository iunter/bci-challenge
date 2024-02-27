package com.ivan.bci.evaluacion.service;


import com.ivan.bci.evaluacion.controller.UserController;
import com.ivan.bci.evaluacion.model.UserRequest;
import com.ivan.bci.evaluacion.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

public class UserControllerTest
{
	@Test
	public void addUserTest()
	{
		UserService service = Mockito.mock(UserService.class);

		UserController userController = new UserController(service);

		UserRequest userRequest = UserRequest.builder()
				.email("email@email.com")
				.name("name")
				.password("password")
				.phones(Collections.emptyList())
				.build();

		userController.newUser(userRequest);

		Mockito.verify(service).addUser(userRequest);
	}
}
