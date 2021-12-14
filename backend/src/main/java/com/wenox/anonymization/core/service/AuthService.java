package com.wenox.anonymization.core.service;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.dto.UserRequest;
import com.wenox.anonymization.core.dto.UserResponse;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.security.service.JwtService;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public AuthService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ApiResponse register(UserRequest dto) {
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");
    }

    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.USER);
    user.setBlocked(false);

    userRepository.save(user);

    return new ApiResponse("Your account was registered successfully.");
  }

  public UserResponse getMe(Authentication authentication) {
    final var principal = (UserDetails) authentication.getPrincipal();

    final var userDb = userRepository.findByEmail(principal.getUsername()).orElseThrow();

    return new UserResponse(userDb.getEmail(), userDb.getRole());
  }

  public UserResponse getMe2(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

      String token = authorizationHeader.substring("Bearer ".length());

      try {
        if (!jwtService.isAccessToken(token)) {
          throw new RuntimeException("Must provide access token.");
        }

        final var expiration = jwtService.getExpirationFromToken(token);

        if (LocalDateTime.now().isAfter(expiration)) {
          throw new RuntimeException("Access token has expired!");
        }

        final var username = jwtService.getUsernameFromToken(token);
        final var dbUser = userRepository.findByEmail(username).orElseThrow();

        System.out.println("Returning response");

        return new UserResponse(dbUser.getEmail(), dbUser.getRole());
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    } else {
      throw new RuntimeException("Must provide access token");
    }
  }
}
