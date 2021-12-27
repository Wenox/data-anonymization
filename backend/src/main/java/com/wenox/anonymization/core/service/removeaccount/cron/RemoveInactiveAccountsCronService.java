package com.wenox.anonymization.core.service.removeaccount.cron;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RemoveInactiveAccountsCronService {

  private static final Logger log = LoggerFactory.getLogger(RemoveInactiveAccountsCronService.class);

  @Value("${core.removeInactiveAccounts.removeAfterTimeInSeconds}")
  private Long removeAfter;

  private final UserRepository userRepository;

  public RemoveInactiveAccountsCronService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Scheduled(cron = "${core.removeInactiveAccounts.cron}")
  public void removeInactiveAccounts() {
    log.info("Started removing inactive accounts...");

    List<User> candidates = userRepository.findAllByForceRemovalFalseAndStatusNot(UserStatus.REMOVED);
    List<User> removedUsers = candidates
        .stream()
        .filter(user -> Optional.ofNullable(user.getLastLoginDate())
            .map(date -> date.plusSeconds(removeAfter).isAfter(LocalDateTime.now()))
            .orElse(false))
        .peek(user -> user.setForceRemoval(true))
        .collect(Collectors.toList());
    userRepository.saveAll(removedUsers);

    log.info("Successfully removed {} inactive accounts.", removedUsers.size());
  }
}
