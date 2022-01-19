package com.wenox.scheduler.service.cron;

import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import com.wenox.users.repository.UserRepository;
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
public class RemoveInactiveAccountsCronService implements CronService {

  private static final Logger log = LoggerFactory.getLogger(RemoveInactiveAccountsCronService.class);

  private final Long removeAfter;
  private final String cronExpression;
  private final boolean isScheduled;
  private final boolean isExecutable;
  private final String description;
  private final UserRepository userRepository;

  public RemoveInactiveAccountsCronService(@Value("${scheduler.removeInactiveAccounts.removeAfterTimeInSeconds}") Long removeAfter,
                                           @Value("${scheduler.removeInactiveAccounts.cron}") String cronExpression,
                                           @Value("${scheduler.removeInactiveAccounts.scheduled}") boolean isScheduled,
                                           @Value("${scheduler.removeInactiveAccounts.executable}") boolean isExecutable,
                                           @Value("${scheduler.removeInactiveAccounts.description}") String description,
                                           UserRepository userRepository) {
    this.removeAfter = removeAfter;
    this.cronExpression = cronExpression;
    this.isScheduled = isScheduled;
    this.isExecutable = isExecutable;
    this.description = description;
    this.userRepository = userRepository;
  }

  @Override
  @Scheduled(cron = "${scheduler.removeInactiveAccounts.cron}")
  public void execute() {
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

  @Override
  public boolean isScheduled() {
    return isScheduled;
  }

  @Override
  public boolean isExecutable() {
    return isExecutable;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getCronExpression() {
    return cronExpression;
  }
}
