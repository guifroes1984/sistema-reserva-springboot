package br.com.guifroes1984.SistemaReservaServico.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "MzI2b3VkZXZObXNkZjY0MjU1ZG9tclR1eTA1NTVkc3R1MjQyVHlE";

	private String criarToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256) // Nova assinatura
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return criarToken(claims, userName);
	}

	private Claims extrairAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
	}

	public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extrairAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Date extrairExpiration(String token) {
		return extrairClaim(token, Claims::getExpiration);
	}

	public String extrairUsername(String token) {
		return extrairClaim(token, Claims::getSubject);
	}

	private Boolean isTokenExpired(String token) {
		return extrairExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extrairUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
