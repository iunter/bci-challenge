package com.ivan.bci.evaluacion.service;

import com.ivan.bci.evaluacion.model.Phone;
import com.ivan.bci.evaluacion.model.User;
import com.ivan.bci.evaluacion.model.UserRequest;
import com.ivan.bci.evaluacion.repository.IPhoneRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ivan.bci.evaluacion.repository.IUserRepository;

import java.util.Date;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService
{

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPhoneRepository phoneRepository;

	@Autowired
	private JwtService jwtService;

	private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	@Value("${PASSWORD_REGEX}")
	private String PASSWORD_REGEX;

	public User addUser(UserRequest userRequest)
	{
		User user = userRepository.findByEmail(userRequest.getEmail());

		if (user != null)
		{
			throw new UserServiceException("El correo ya ha sido registrado");
		}

		if (!validateRegex(userRequest.getEmail(), EMAIL_REGEX))
		{
			throw new UserServiceException("El correo no tiene un formato valido");
		}

		if (!validateRegex(userRequest.getPassword(), PASSWORD_REGEX))
		{
			throw new UserServiceException("La contraseña no cumple con los requerimientos");
		}


		return populateUser(userRequest);
	}

	private boolean validateRegex(String string, String regex)
	{
		return string.matches(regex);
	}

	class UserServiceException extends RuntimeException {
		UserServiceException(String message) {
			super(message);
		}
	}

	private User populateUser(UserRequest userRequest)
	{
		User user = User.builder()
				.name(userRequest.getName())
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())
				.phoneList(userRequest.getPhones())
				.created(new Date())
				.modified(new Date())
				.lastLogin(new Date())
				.token(jwtService.generateToken(userRequest.getEmail()))
				.isActive(true)
				.build();

		User newUser = userRepository.save(user);

		for (Phone phone : user.getPhoneList())
		{
			phone.setUser(newUser);
			phoneRepository.save(phone);
		}

		return newUser;
	}


}
