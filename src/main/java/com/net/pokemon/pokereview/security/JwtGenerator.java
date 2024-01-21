package com.net.pokemon.pokereview.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;

@Component
public class JwtGenerator {
  public String generatedToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiringDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

    // Key hcmaKey =
    // Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_SECRET));

    String token = Jwts.builder()
        .subject(username)
        .issuedAt(currentDate)
        .expiration(expiringDate)
        .signWith(generatePublicKey())
        .compact();

    return token;
  }

  public String getUsernameFromJwt(String token) {
    String result = Jwts
        .parser()
        .verifyWith(generatePublicKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();

    System.out.println("Username: " + result);

    return result;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(generatePublicKey()).build().parseSignedClaims(token);
      return true;
    } catch (Exception ex) {
      throw new AuthenticationCredentialsNotFoundException("Invalid token or expired");
    }
  }

  public SecretKey generatePublicKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_SECRET));
  }

}
