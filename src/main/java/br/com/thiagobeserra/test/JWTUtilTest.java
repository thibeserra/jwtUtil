package br.com.thiagobeserra.test;

import java.security.Key;

import br.com.thiagobeserra.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JWTUtilTest {
	
	public static void main(String[] args) {
		
		//Cria key de assinatura do token
		Key key = JWTUtil.getKey();
		
		//Cria key de assinatura do token
		Key key2 = JWTUtil.getKey(); 
		
		//Gera token e assina com key gerada
		String token = JWTUtil.getToken(key);
		
		System.out.println(token);
		
		//verifica se assinatura do token é valida para a key - true
		try {
			JWTUtil.validateSignatureToken(key, token);
			//Verifica se getSubject do token é valido
			System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject().equals("maria.alves"));
			
			@SuppressWarnings("rawtypes")
			Jwt tokenObj = JWTUtil.deserializeToken(key, token);
			
			System.out.println("Algoritmo: " + tokenObj.getHeader().get("alg"));
			System.out.println(tokenObj.getBody());
		} catch(SignatureException e) {
			System.out.println("T1: Assinatura do Token Inválido!");
		} catch(ExpiredJwtException ex) {
			System.out.println("T1: Token Expirou!");
		}
		
	}
	
}
