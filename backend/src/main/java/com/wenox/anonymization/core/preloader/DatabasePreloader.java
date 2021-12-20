package com.wenox.anonymization.core.preloader;

import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Preloader
public class DatabasePreloader {

  @Bean
  public CommandLineRunner loadDatabaseContent(UserRepository userRepository,
                                               PasswordEncoder passwordEncoder) {
    return args -> {
      User admin = new User();
      admin.setRole(Role.ADMIN);
      admin.setEmail("admin@admin.com");
      admin.setPassword(passwordEncoder.encode("admin"));
      admin.setStatus(UserStatus.ACTIVE);
      userRepository.save(admin);

      User user = new User();
      user.setRole(Role.USER);
      user.setEmail("user@user.com");
      user.setPassword(passwordEncoder.encode("user"));
      user.setStatus(UserStatus.ACTIVE);
      userRepository.save(user);
    };
  }
}
