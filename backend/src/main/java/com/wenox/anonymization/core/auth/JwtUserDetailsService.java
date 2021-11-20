package com.wenox.anonymization.core.auth;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserByUsername(String email) {
    final var user = userRepository.findByEmail(email).orElseThrow();

    user.setLastLogin(LocalDateTime.now());

    userRepository.save(user);

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        true,
        true,
        true,
        !user.isBlocked(),
        List.of(new SimpleGrantedAuthority(user.getRole().name()))
    );
  }
}
