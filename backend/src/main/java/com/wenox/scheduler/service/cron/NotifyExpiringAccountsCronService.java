package com.wenox.scheduler.service.cron;

import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import com.wenox.users.repository.UserRepository;
import com.wenox.users.service.mail.MailDescription;
import com.wenox.users.service.mail.MailService;
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
public class NotifyExpiringAccountsCronService implements CronService {

  private static final Logger log = LoggerFactory.getLogger(NotifyExpiringAccountsCronService.class);

  private final Long notifyAfter;
  private final String cronExpression;
  private final boolean isScheduled;
  private final boolean isExecutable;
  private final String description;
  private final UserRepository userRepository;
  private final MailService mailService;

  public NotifyExpiringAccountsCronService(@Value("${core.notifyExpiringAccounts.notifyAfterTimeInSeconds}") Long notifyAfter,
                                           @Value("${core.notifyExpiringAccounts.cron}") String cronExpression,
                                           @Value("${core.notifyExpiringAccounts.scheduled}") boolean isScheduled,
                                           @Value("${core.notifyExpiringAccounts.executable}") boolean isExecutable,
                                           @Value("${core.notifyExpiringAccounts.description}") String description,
                                           UserRepository userRepository,
                                           MailService mailService) {
    this.notifyAfter = notifyAfter;
    this.cronExpression = cronExpression;
    this.isScheduled = isScheduled;
    this.isExecutable = isExecutable;
    this.description = description;
    this.userRepository = userRepository;
    this.mailService = mailService;
  }

  @Override
  @Scheduled(cron = "${core.notifyExpiringAccounts.cron}")
  public void execute() {
    log.info("Started cron service: notifying expiring accounts...");

    List<User> candidates = userRepository.findAllByForceRemovalFalseAndMarkedForRemovalFalseAndStatus(UserStatus.ACTIVE);
    List<User> expiringUsers = candidates
        .stream()
        .filter(user -> Optional.ofNullable(user.getLastLoginDate())
            .map(date -> date.plusSeconds(notifyAfter).isAfter(LocalDateTime.now()))
            .orElse(false))
        .collect(Collectors.toList());

    expiringUsers.forEach(user -> mailService.sendMail(
        new MailDescription(user.getEmail(), "Your account will expire soon",
            "Dear user, due to inactivity on your account, it will be soon removed. Please login to prevent the removal.")));

    log.info("Successfully notified {} expiring accounts.", expiringUsers.size());
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
