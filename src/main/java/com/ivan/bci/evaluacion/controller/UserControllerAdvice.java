package com.ivan.bci.evaluacion.controller;

import com.ivan.bci.evaluacion.dto.ErrorMessageDto;
import com.ivan.bci.evaluacion.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler
{
	@ResponseBody
	@ExceptionHandler(UserServiceImpl.UserServiceException.class)
	protected ResponseEntity<Object> handleBadRequest(UserServiceImpl.UserServiceException ex, WebRequest request)
	{
		ErrorMessageDto errorMessageDto = ErrorMessageDto.builder()
				.message(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, errorMessageDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}