package com.wenox.anonymization.core.security.service;

import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.repository.UserRepository;
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

    user.setLastLoginDate(LocalDateTime.now());

    userRepository.save(user);

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        true,
        true,
        true,
        user.getStatus() == UserStatus.ACTIVE && !user.isMarkedForRemoval() && !user.isForceRemoval(),
        List.of(new SimpleGrantedAuthority(user.getRole().name()))
    );
  }
}
