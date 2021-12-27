package com.wenox.anonymization.core.service.verifymail;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.security.filter.JwtAuthenticationFilter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RemoveUnverifiedAccountsCronService {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  @Value("${core.removeUnverifiedAccounts.removeAfterTimeInSeconds}")
  private Long removeAfter;

  private final UserRepository userRepository;

  public RemoveUnverifiedAccountsCronService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Scheduled(cron = "${core.removeUnverifiedAccounts.cron}")
  public void removeUnverifiedAccounts() {
    log.info("Started removing unverified accounts...");

    List<User> candidates = userRepository.findAllByVerifiedFalseAndForceRemovalFalseAndStatusNot(UserStatus.REMOVED);
    List<User> removedUsers = candidates
        .stream()
        .filter(user -> user.getRegisteredDate().plusSeconds(removeAfter).isBefore(LocalDateTime.now()))
        .peek(user -> user.setForceRemoval(true))
        .collect(Collectors.toList());
    userRepository.saveAll(removedUsers);

    log.info("Successfully removed {} unverified accounts.", removedUsers.size());
  }
}
