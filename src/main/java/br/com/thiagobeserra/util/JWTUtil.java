package br.com.thiagobeserra.util;

import java.security.Key;

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
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
		
		return compactJWS;
	}
	
	public static void validateSignatureToken(Key key, String token) throws SignatureException {
		Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}

}
