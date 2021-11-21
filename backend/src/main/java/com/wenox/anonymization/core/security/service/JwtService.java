package com.wenox.anonymization.core.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${core.jwt.secretKey}")
  private String secretKey;

  @Value("${core.jwt.algorithm}")
  private SignatureAlgorithm algorithm;

  @Value("${core.jwt.accessToken.expireTimeInSeconds}")
  private Long accessTokenExpireTime;

  @Value("${core.jwt.refreshToken.expireTimeInSeconds}")
  private Long refreshTokenExpireTime;

  private SecretKey signingKey;

  @PostConstruct
  public void init() {
    byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
    signingKey = new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());
  }

  public String getRoleFromToken(String token) {
    return getClaimFromToken(token, claims -> claims.get("role", String.class));
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String generateAccessTokenFor(User user, String issuer) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuer(issuer)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTime * 1000))
        .claim("role", user.getAuthorities().iterator().next().getAuthority())
        .signWith(signingKey, algorithm)
        .compact();
  }

  public String generateRefreshTokenFor(User user, String issuer) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuer(issuer)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime * 1000))
        .signWith(signingKey, algorithm)
        .compact();
  }
}
