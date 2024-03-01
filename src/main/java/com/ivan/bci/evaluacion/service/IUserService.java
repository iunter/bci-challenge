package com.ivan.bci.evaluacion.service;

import com.ivan.bci.evaluacion.dto.UserRequestDto;
import com.ivan.bci.evaluacion.model.UserModel;

public interface IUserService
{
	UserModel addUser(UserRequestDto userRequestDto);
}
