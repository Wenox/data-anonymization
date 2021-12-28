package com.wenox.anonymization.core.service.task.cronservice;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.service.mail.MailDescription;
import com.wenox.anonymization.core.service.mail.MailService;
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
public class HandleRemoveAccountsCronService implements CronService {

  private static final Logger log = LoggerFactory.getLogger(HandleRemoveAccountsCronService.class);

  @Value("${core.handleRemoveAccounts.markedRemoveAfterTimeInSeconds}")
  private Long removeAfter;

  @Value("${core.handleRemoveAccounts.cron}")
  private String cronExpression;

  @Value("${core.handleRemoveAccounts.scheduled}")
  private boolean isScheduled;

  @Value("${core.handleRemoveAccounts.executable}")
  private boolean isExecutable;

  @Value("${core.handleRemoveAccounts.description}")
  private String description;

  private final UserRepository userRepository;
  private final MailService mailService;

  public HandleRemoveAccountsCronService(UserRepository userRepository, MailService mailService) {
    this.userRepository = userRepository;
    this.mailService = mailService;
  }

  @Override
  @Scheduled(cron = "${core.handleRemoveAccounts.cron}")
  public void execute() {
    log.info("Started cron service: handling the removal of accounts...");

    List<User> candidates = userRepository.findAllByForceRemovalTrueOrMarkedForRemovalTrueAndStatusNot(UserStatus.REMOVED);
    List<User> removedUsers = candidates
        .stream()
        .filter(user -> user.isForceRemoval() || (user.isMarkedForRemoval() && Optional
            .ofNullable(user.getRemovalRequestedDate())
            .map(date -> date.plusSeconds(removeAfter).isAfter(LocalDateTime.now()))
            .orElse(false)))
        .collect(Collectors.toList());

    removedUsers.forEach(user -> mailService.sendMail(new MailDescription(user.getEmail(), "Your account was removed",
        "Dear user, your account was removed from our services.")));

    log.info("Successfully removed {} pending for removal accounts.", removedUsers.size());
  }

  public boolean isScheduled() {
    return isScheduled;
  }

  public boolean isExecutable() {
    return isExecutable;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String getCronExpression() {
    return cronExpression;
  }
}
