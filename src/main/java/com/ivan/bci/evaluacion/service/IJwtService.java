package com.ivan.bci.evaluacion.service;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface IJwtService
{
	String generateToken(String email);

	<T> T extractClaim(String token, Function<Claims, T> claimsTFunction);

	Boolean isTokenExpired(String token);

}
