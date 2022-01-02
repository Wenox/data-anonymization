package com.wenox.infrastructure.preloader;

import com.wenox.users.domain.Role;
import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import com.wenox.users.domain.VerifyMailToken;
import com.wenox.users.repository.UserRepository;
import com.wenox.users.repository.VerifyMailTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Preloader
public class DatabasePreloader {

  @Bean
  public CommandLineRunner loadDatabaseContent(UserRepository userRepository,
                                               PasswordEncoder passwordEncoder,
                                               VerifyMailTokenRepository verifyMailTokenRepository) {
    return args -> {
      User admin = new User();
      admin.setRole(Role.ADMIN);
      admin.setEmail("admin@admin.com");
      admin.setPassword(passwordEncoder.encode("admin"));
      admin.setStatus(UserStatus.ACTIVE);
      admin.setVerified(true);
      admin.setFirstName("Tadeusz");
      admin.setLastName("Jankowski");
      admin.setPurpose("Research and Development");
      userRepository.save(admin);

      User admin1 = new User();
      admin1.setRole(Role.ADMIN);
      admin1.setEmail("admin1@admin1.com");
      admin1.setPassword(passwordEncoder.encode("admin1"));
      admin1.setStatus(UserStatus.BLOCKED);
      admin1.setVerified(true);
      userRepository.save(admin1);

      User admin2 = new User();
      admin2.setRole(Role.ADMIN);
      admin2.setEmail("admin2@admin2.com");
      admin2.setPassword(passwordEncoder.encode("admin2"));
      admin2.setStatus(UserStatus.REMOVED);
      admin2.setVerified(false);
      userRepository.save(admin2);

      User user = new User();
      user.setRole(Role.VERIFIED_USER);
      user.setEmail("user@user.com");
      user.setPassword(passwordEncoder.encode("user"));
      user.setStatus(UserStatus.ACTIVE);
      user.setVerified(true);
      user.setFirstName("Janina");
      user.setLastName("Kowalska");
      user.setPurpose("Scentific research");
      userRepository.save(user);

      User user1 = new User();
      user1.setRole(Role.VERIFIED_USER);
      user1.setEmail("user1@user1.com");
      user1.setPassword(passwordEncoder.encode("user1"));
      user1.setStatus(UserStatus.ACTIVE);
      user1.setMarkedForRemoval(true);
      user1.setVerified(false);
      userRepository.save(user1);

      User user2 = new User();
      user2.setRole(Role.VERIFIED_USER);
      user2.setEmail("user2@user2.com");
      user2.setPassword(passwordEncoder.encode("user2"));
      user2.setStatus(UserStatus.ACTIVE);
      user2.setVerified(true);
      userRepository.save(user2);

      User user3 = new User();
      user3.setRole(Role.UNVERIFIED_USER);
      user3.setEmail("unverified@unverified.com");
      user3.setPassword(passwordEncoder.encode("unverified"));
      user3.setStatus(UserStatus.ACTIVE);
      user3.setMarkedForRemoval(false);
      user3.setVerified(false);
      user3.setFirstName("John");
      user3.setLastName("Hoowder");
      user3.setPurpose("GDPR compliance");
      userRepository.save(user3);

      VerifyMailToken verifyMailToken = new VerifyMailToken();
      verifyMailToken.setToken(UUID.randomUUID().toString());
      verifyMailToken.setExpirationDate(LocalDateTime.now().plusSeconds(60));
      verifyMailToken.setUser(user3);
      verifyMailTokenRepository.save(verifyMailToken);
    };
  }
}
