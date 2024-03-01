package com.ivan.bci.evaluacion.service.impl;

import com.ivan.bci.evaluacion.model.PhoneModel;
import com.ivan.bci.evaluacion.model.UserModel;
import com.ivan.bci.evaluacion.dto.UserRequestDto;
import com.ivan.bci.evaluacion.repository.IPhoneRepository;
import com.ivan.bci.evaluacion.service.IJwtService;
import com.ivan.bci.evaluacion.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ivan.bci.evaluacion.repository.IUserRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements IUserService
{

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPhoneRepository phoneRepository;

	@Autowired
	private IJwtService jwtService;

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	@Value("${PASSWORD_REGEX}")
	private String PASSWORD_REGEX;

	/**
	 *
	 * @param userRequestDto request para la creación de usuario
	 * @return Usuario creado
	 * @throws UserServiceException si hay un error (usuario ya existente, correo o password con formato inválido)
	 */
	@Override
	public UserModel addUser(UserRequestDto userRequestDto) throws UserServiceException
	{
		UserModel userModel = userRepository.findByEmail(userRequestDto.getEmail());

		if (userModel != null)
		{
			throw new UserServiceException("El correo ya ha sido registrado");
		}

		if (!validateRegex(userRequestDto.getEmail(), EMAIL_REGEX))
		{
			throw new UserServiceException("El correo no tiene un formato válido");
		}

		if (!validateRegex(userRequestDto.getPassword(), PASSWORD_REGEX))
		{
			throw new UserServiceException("La contraseña no cumple con los requerimientos");
		}


		return populateUser(userRequestDto);
	}

	private boolean validateRegex(String string, String regex)
	{
		return string.matches(regex);
	}

	private UserModel populateUser(UserRequestDto userRequestDto)
	{
		UserModel userModel = UserModel.builder()
				.name(userRequestDto.getName())
				.email(userRequestDto.getEmail())
				.password(userRequestDto.getPassword())
				.phoneList(userRequestDto.getPhones())
				.created(new Date())
				.modified(new Date())
				.token(jwtService.generateToken(userRequestDto.getEmail()))
				.build();

		UserModel newUserModel = userRepository.save(userModel);

		List<PhoneModel> phoneModelList = userModel.getPhoneList();

		phoneModelList.forEach(phoneModel ->
			{
				phoneModel.setUser(newUserModel);
				phoneRepository.save(phoneModel);
			});

		return newUserModel;
	}

	static class UserServiceException extends RuntimeException
	{
		UserServiceException(String message)
		{
			super(message);
		}
	}

	@ControllerAdvice
	class UserServiceAdvice
	{
		@ResponseBody
		@ExceptionHandler(UserServiceException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		String UserServiceExceptionHandler(UserServiceException ex)
		{
			return ex.getMessage();
		}
	}


}
