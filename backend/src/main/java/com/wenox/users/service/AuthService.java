package com.wenox.users.service;

import com.wenox.users.domain.UserStatus;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.domain.Role;
import com.wenox.users.domain.User;
import com.wenox.users.dto.RegisterUserRequest;
import com.wenox.users.repository.UserRepository;
import com.wenox.users.service.verifymail.VerifyMailService;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final VerifyMailService verifyMailService;

  public AuthService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     VerifyMailService verifyMailService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.verifyMailService = verifyMailService;
  }

  @Transactional
  public ApiResponse register(RegisterUserRequest dto) {
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");
    }

    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.UNVERIFIED_USER);
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setPurpose(dto.getPurpose());
    user.setStatus(UserStatus.ACTIVE);
    user.setVerified(false);
    user.setMarkedForRemoval(false);
    user.setForceRemoval(false);
    user.setRegisteredDate(LocalDateTime.now());

    userRepository.save(user);

    verifyMailService.sendVerificationMailToUser(user);

    return new ApiResponse("Your account was registered successfully.");
  }

  public User getMe(Authentication authentication) {
    final var principal = (UserDetails) authentication.getPrincipal();
    return userRepository.findByEmail(principal.getUsername()).orElseThrow();
  }
}
