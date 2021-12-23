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

      User admin1 = new User();
      admin1.setRole(Role.ADMIN);
      admin1.setEmail("admin1@admin1.com");
      admin1.setPassword(passwordEncoder.encode("admin1"));
      admin1.setStatus(UserStatus.BLOCKED);
      userRepository.save(admin1);

      User admin2 = new User();
      admin2.setRole(Role.ADMIN);
      admin2.setEmail("admin2@admin2.com");
      admin2.setPassword(passwordEncoder.encode("admin2"));
      admin2.setStatus(UserStatus.REMOVED);
      userRepository.save(admin2);

      User user = new User();
      user.setRole(Role.USER);
      user.setEmail("user@user.com");
      user.setPassword(passwordEncoder.encode("user"));
      user.setStatus(UserStatus.ACTIVE);
      userRepository.save(user);

      User user1 = new User();
      user1.setRole(Role.USER);
      user1.setEmail("user@user.com");
      user1.setPassword(passwordEncoder.encode("user"));
      user1.setStatus(UserStatus.ACTIVE);
      user1.setMarkedForRemoval(true);
      userRepository.save(user1);
    };
  }
}
