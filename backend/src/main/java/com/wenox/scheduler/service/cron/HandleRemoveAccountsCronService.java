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
public class HandleRemoveAccountsCronService implements CronService {

  private static final Logger log = LoggerFactory.getLogger(HandleRemoveAccountsCronService.class);

  private final Long removeAfter;
  private final String cronExpression;
  private final boolean isScheduled;
  private final boolean isExecutable;
  private final String description;
  private final UserRepository userRepository;
  private final MailService mailService;

  public HandleRemoveAccountsCronService(@Value("${core.handleRemoveAccounts.markedRemoveAfterTimeInSeconds}") Long removeAfter,
                                         @Value("${core.handleRemoveAccounts.cron}") String cronExpression,
                                         @Value("${core.handleRemoveAccounts.scheduled}") boolean isScheduled,
                                         @Value("${core.handleRemoveAccounts.executable}") boolean isExecutable,
                                         @Value("${core.handleRemoveAccounts.description}") String description,
                                         UserRepository userRepository,
                                         MailService mailService) {
    this.removeAfter = removeAfter;
    this.cronExpression = cronExpression;
    this.isScheduled = isScheduled;
    this.isExecutable = isExecutable;
    this.description = description;
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
