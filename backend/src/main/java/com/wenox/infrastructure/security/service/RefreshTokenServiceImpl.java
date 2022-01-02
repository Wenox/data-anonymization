package com.wenox.infrastructure.security.service;

import com.wenox.users.repository.UserRepository;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final JwtService jwtService;

  private final UserRepository userRepository;

  public RefreshTokenServiceImpl(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  public void generateTokens(HttpServletRequest request, HttpServletResponse response) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

      String token = authorizationHeader.substring("Bearer ".length());

      try {
        if (!jwtService.isRefreshToken(token)) {
          throw new RuntimeException("Must provide refresh token.");
        }

        final var expiration = jwtService.getExpirationFromToken(token);

        if (LocalDateTime.now().isAfter(expiration)) {
          throw new RuntimeException("Refresh token expired!");
        }

        final var username = jwtService.getUsernameFromToken(token);
        final var dbUser = userRepository.findByEmail(username).orElseThrow();

        UserJwt user = new UserJwt(dbUser.getEmail(), dbUser.getRole().name());

        String accessToken = jwtService.generateAccessTokenFor(user);
        String refreshToken = jwtService.generateRefreshTokenFor(user);

        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
      } catch (Exception ex) {
        ex.printStackTrace();
        response.setStatus(403);
      }
    }
  }
}
