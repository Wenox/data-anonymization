package com.wenox.anonymization.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;

    setFilterProcessesUrl("/api/v1/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserRequest dto = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
      log.info("email: {}, password: {}", dto.getEmail(), dto.getPassword());
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
      return authenticationManager.authenticate(authenticationToken);
    } catch (Exception ex) {
      log.error("Logging in failed.");
      throw new AuthenticationException("Logging in failed") {
      };
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) {

    User user = (User) authResult.getPrincipal();

    final var accessToken = Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuer(request.getRequestURI())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 120 * 1000))
        .claim("role", user.getAuthorities().iterator().next().getAuthority())
        .signWith(jwtService.getKey(), SignatureAlgorithm.HS256)
        .compact();

    final var refreshToken = Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuer(request.getRequestURI())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 120000 * 1000))
        .signWith(jwtService.getKey(), SignatureAlgorithm.HS256)
        .compact();

    log.info("Generated accessToken: {}", accessToken);
    log.info("Generated refreshToken: {}", refreshToken);

    response.setHeader("access_token", accessToken);
    response.setHeader("refresh_token", refreshToken);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException failed) throws IOException, ServletException {
    log.error("Unsuccessful authentication");
    super.unsuccessfulAuthentication(request, response, failed);
  }
}
