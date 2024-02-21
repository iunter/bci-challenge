package com.ivan.bci.evaluacion.service;

import com.ivan.bci.evaluacion.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService
{
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;

	@Value("${jwt.duration}")
	private int EXPIRATION;

	public String generateToken(String userName)
	{
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	public String extractEmail(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction)
	{
		Claims claims = extractAllClaims(token);
		return claimsTFunction.apply(claims);
	}

	public Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token)
	{
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private String createToken(Map<String, Object> claims, String userMail)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userMail)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, User user) {
		final String email = extractEmail(token);
		return (email.equals(user.getEmail()) && !isTokenExpired(token));
	}
}