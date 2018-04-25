package br.com.thiagobeserra.util;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public abstract class JWTUtil {
	
	public static Key getKey() {
		
		return MacProvider.generateKey();
	}
	
	public static String getToken(Key key) {
		String compactJWS = Jwts.builder()
				.setSubject("maria.alves")
				.setExpiration(timeToExpireToken(1))
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
		
		return compactJWS;
	}
	
	@SuppressWarnings("rawtypes")
	public static Jwt deserializeToken(Key key, String token) {
		Jwt jwt = Jwts.parser().setSigningKey(key).parse(token);
		
		return jwt;
	}
	
	public static void validateSignatureToken(Key key, String token) throws SignatureException {
		Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
	
	public static Date timeToExpireToken(int seconds) {
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		currentDate.add(Calendar.SECOND, seconds);
		
		return currentDate.getTime();
	}

}
