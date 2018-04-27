package br.com.thiagobeserra.util;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public abstract class JWTUtil {
	
	/**
	 * Metodo que gera chave secreta (key)
	 * @return
	 */
	public static Key getKey() {
		
		return MacProvider.generateKey();
	}
	
	/**
	 * Gera token JWT assinado e com tempo em segundos para expiração
	 * @param key
	 * @param subject
	 * @param secondsToExpiration
	 * @return
	 */
	public static String getToken(Key key, String issuer, String subject, Integer secondsToExpiration) {
		String compactJWS = Jwts.builder()
				.setIssuer(issuer)
				.setSubject(subject)
				.setExpiration(timeToExpireToken(secondsToExpiration))
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
		
		return compactJWS;
	}
	
	/**
	 * Valida assinatura e tempo de expiração do token
	 * @param key
	 * @param token
	 * @throws SignatureException
	 */
	public static void validateSignatureToken(Key key, String token) throws SignatureException, ExpiredJwtException {
		Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
	
	/**
	 * Método auxiliar para adicionar os segundos para o token expirar baseado na data atual
	 * @param seconds
	 * @return
	 */
	private static Date timeToExpireToken(int seconds) {
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		currentDate.add(Calendar.SECOND, seconds);
		
		return currentDate.getTime();
	}

}
