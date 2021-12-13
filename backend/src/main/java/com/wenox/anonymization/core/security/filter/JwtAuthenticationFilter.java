package com.wenox.anonymization.core.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenox.anonymization.core.dto.UserJwt;
import com.wenox.anonymization.core.dto.UserRequest;
import com.wenox.anonymization.core.security.service.JwtService;
import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    UserJwt user = UserJwt.from((User) authResult.getPrincipal());

    String accessToken = jwtService.generateAccessTokenFor(user);
    String refreshToken = jwtService.generateRefreshTokenFor(user);

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
