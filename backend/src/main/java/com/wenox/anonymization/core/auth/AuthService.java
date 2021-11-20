package com.wenox.anonymization.core.auth;

import com.wenox.anonymization.core.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ApiResponse register(RegisterRequest dto) {
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.USER);
    user.setBlocked(false);

    userRepository.save(user);

    return new ApiResponse("Your account was registered successfully.");
  }
}
