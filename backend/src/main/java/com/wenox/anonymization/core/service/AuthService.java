package com.wenox.anonymization.core.service;

import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.dto.RegisterUserRequest;
import com.wenox.anonymization.core.repository.UserRepository;
import java.time.LocalDateTime;
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

  public AuthService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ApiResponse register(RegisterUserRequest dto) {
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");
    }

    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.USER);
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setPurpose(dto.getPurpose());
    user.setStatus(UserStatus.ACTIVE);
    user.setVerified(false);
    user.setMarkedForRemoval(false);
    user.setForceRemoval(false);
    user.setRegisteredDate(LocalDateTime.now());

    userRepository.save(user);

    return new ApiResponse("Your account was registered successfully.");
  }

  public User getMe(Authentication authentication) {
    final var principal = (UserDetails) authentication.getPrincipal();
    return userRepository.findByEmail(principal.getUsername()).orElseThrow();
  }
}
