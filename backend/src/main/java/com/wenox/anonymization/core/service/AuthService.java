package com.wenox.anonymization.core.service;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.dto.UserRequest;
import com.wenox.anonymization.core.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
}
