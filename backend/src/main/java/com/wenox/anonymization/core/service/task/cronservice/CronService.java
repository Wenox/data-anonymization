package com.wenox.anonymization.core.service.task.cronservice;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.scheduling.support.CronExpression;

public interface CronService {

  void execute();

  boolean isScheduled();

  boolean isExecutable();

  String getDescription();

  String getCronExpression();

  default Optional<LocalDateTime> nextScheduledExecution() {
    if (!isScheduled()) {
      return Optional.empty();
    }
    return Optional.ofNullable(CronExpression.parse(getCronExpression()).next(LocalDateTime.now()));
  }

  default String getTaskName() {
    return AopProxyUtils.ultimateTargetClass(this).getSimpleName();
  }
}
