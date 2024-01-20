package com.net.pokemon.pokereview.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;

public class JwtGenerator {
  public String generatedToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiringDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

    Key hcmaKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_SECRET));

    String token = Jwts.builder()
        .subject(username)
        .issuedAt(currentDate)
        .expiration(expiringDate)
        .signWith(hcmaKey)
        .compact();

    return token;
  }

  public String getUsernameFromJwt(String token) {
    return "";
  }

}
