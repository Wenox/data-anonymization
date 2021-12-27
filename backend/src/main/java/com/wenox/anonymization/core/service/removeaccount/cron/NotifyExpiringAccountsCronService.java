package com.wenox.anonymization.core.service.removeaccount.cron;

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
public class NotifyExpiringAccountsCronService {

  private static final Logger log = LoggerFactory.getLogger(NotifyExpiringAccountsCronService.class);

  @Value("${core.notifyExpiringAccounts.notifyAfterTimeInSeconds}")
  private Long notifyAfter;

  private final UserRepository userRepository;
  private final MailService mailService;

  public NotifyExpiringAccountsCronService(UserRepository userRepository, MailService mailService) {
    this.userRepository = userRepository;
    this.mailService = mailService;
  }

  @Scheduled(cron = "${core.notifyExpiringAccounts.cron}")
  public void notfyExpiringAccounts() {
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
}
