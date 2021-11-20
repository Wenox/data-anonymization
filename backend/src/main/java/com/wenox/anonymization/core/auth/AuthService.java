package com.wenox.anonymization.core.auth;

import com.wenox.anonymization.core.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ApiResponse register(RegisterRequest dto) {
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setRole(Role.USER);
    user.setBlocked(false);

    userRepository.save(user);

    return new ApiResponse("Your account was registered successfully.");
  }
}
