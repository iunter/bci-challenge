package com.ivan.bci.evaluacion.service;

import com.ivan.bci.evaluacion.model.Phone;
import com.ivan.bci.evaluacion.model.User;
import com.ivan.bci.evaluacion.model.UserRequest;
import com.ivan.bci.evaluacion.repository.IPhoneRepository;
import com.ivan.bci.evaluacion.repository.IUserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceTest
{

	private final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

	@Test
	public void addUserTest()
	{
		IUserRepository userRepository = Mockito.mock(IUserRepository.class);

		IPhoneRepository phoneRepository = Mockito.mock(IPhoneRepository.class);

		JwtService jwtService = Mockito.mock(JwtService.class);

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());

		Phone phone = Phone.builder()
				.cityCode(1)
				.countryCode(1)
				.number(1)
				.build();

		Mockito.when(phoneRepository.save(Mockito.any(Phone.class))).thenReturn(phone);

		Mockito.when(jwtService.generateToken(Mockito.anyString())).thenReturn("token");

		UserService userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX);

		List<Phone> phones = Collections.singletonList(phone);

		UserRequest userRequest = createRequest("email@email.com", "name", "password123", phones);

		userService.addUser(userRequest);

		Mockito.verify(userRepository).findByEmail(Mockito.eq("email@email.com"));
		Mockito.verify(userRepository).save(Mockito.any(User.class));

		Mockito.verify(phoneRepository).save(Mockito.eq(phone));

		Mockito.verify(jwtService).generateToken("email@email.com");
	}

	@Test
	public void addUserMultiplePhonesTest()
	{
		IUserRepository userRepository = Mockito.mock(IUserRepository.class);

		IPhoneRepository phoneRepository = Mockito.mock(IPhoneRepository.class);

		JwtService jwtService = Mockito.mock(JwtService.class);

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());

		List<Phone> phones = new ArrayList<>();

		phones.add(Phone.builder()
				.cityCode(1)
				.countryCode(1)
				.number(1)
				.build());

		phones.add(Phone.builder()
				.cityCode(2)
				.countryCode(2)
				.number(2)
				.build());


		Mockito.when(phoneRepository.save(Mockito.any(Phone.class))).thenReturn(new Phone());

		Mockito.when(jwtService.generateToken(Mockito.anyString())).thenReturn("token");

		UserService userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX);

		UserRequest userRequest = createRequest("email@email.com", "name", "password123", phones);

		userService.addUser(userRequest);

		Mockito.verify(userRepository).findByEmail(Mockito.eq("email@email.com"));
		Mockito.verify(userRepository).save(Mockito.any(User.class));

		Mockito.verify(phoneRepository, Mockito.times(2)).save(Mockito.any(Phone.class));

		Mockito.verify(jwtService).generateToken("email@email.com");
	}

	@Test()
	public void addUserExistingEmailTest()
	{
		IUserRepository userRepository = Mockito.mock(IUserRepository.class);

		IPhoneRepository phoneRepository = Mockito.mock(IPhoneRepository.class);

		JwtService jwtService = Mockito.mock(JwtService.class);

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(new User());


		UserService userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX);


		UserRequest userRequest = createRequest("email@email.com", "name", "password123", Collections.emptyList());


		Exception exception = Assertions.assertThrows(Exception.class, () -> userService.addUser(userRequest));
		Assertions.assertEquals(exception.getMessage(), "El correo ya ha sido registrado");

	}

	@Test()
	public void addUserWrongEmailTest()
	{
		IUserRepository userRepository = Mockito.mock(IUserRepository.class);

		IPhoneRepository phoneRepository = Mockito.mock(IPhoneRepository.class);

		JwtService jwtService = Mockito.mock(JwtService.class);

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);


		UserService userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX);


		UserRequest userRequest = createRequest("Wrong_Email", "name", "password123", Collections.emptyList());


		Exception exception = Assertions.assertThrows(Exception.class, () -> userService.addUser(userRequest));
		Assertions.assertEquals(exception.getMessage(), "El correo no tiene un formato valido");

	}

	@Test()
	public void addUserWrongPasswordTest()
	{
		IUserRepository userRepository = Mockito.mock(IUserRepository.class);

		IPhoneRepository phoneRepository = Mockito.mock(IPhoneRepository.class);

		JwtService jwtService = Mockito.mock(JwtService.class);

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);


		UserService userService = new UserService(userRepository, phoneRepository, jwtService, PASSWORD_REGEX);


		UserRequest userRequest = createRequest("email@email.com", "name", "a", Collections.emptyList());


		Exception exception = Assertions.assertThrows(Exception.class, () -> userService.addUser(userRequest));
		Assertions.assertEquals(exception.getMessage(), "La contraseña no cumple con los requerimientos");

	}

	private UserRequest createRequest(String email, String name, String password, List<Phone> phoneList)
	{
		return UserRequest.builder()
				.email(email)
				.name(name)
				.password(password)
				.phones(phoneList)
				.build();
	}
}
