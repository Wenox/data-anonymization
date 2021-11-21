package com.wenox.anonymization.core.auth;

import com.wenox.anonymization.core.ApiResponse;
import com.wenox.anonymization.core.domain.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider jwtTokenUtil;

  public AuthService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     AuthenticationManager authenticationManager,
                     TokenProvider jwtTokenUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
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

  public ApiResponse login(UserRequest dto) {
    try {
      var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(auth);
      final String token = jwtTokenUtil.generateToken(auth);
      return new ApiResponse("Logged in successfully. Token: " + token);
    } catch (AuthenticationException e) {
      e.printStackTrace();
      return new ApiResponse("Logging in failed.", false);
    }
  }
}
