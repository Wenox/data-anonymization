package com.wenox.anonymization.core.service.task.cronservice;

import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

public interface CronService {

  void execute();

  boolean isScheduled();

  boolean isExecutable();

  String getDescription();

  default String getTaskName() {
    return AopProxyUtils.ultimateTargetClass(this).getSimpleName();
  }

  default String getCronExpression(Environment env) {
    try {
      Method executeMethod = AopProxyUtils.ultimateTargetClass(this).getMethod("execute");
      String SPeL = Objects.requireNonNull(AnnotationUtils.getAnnotation(executeMethod, Scheduled.class)).cron();
      String property = SPeL.substring(2, SPeL.length() - 1);
      return env.getProperty(property);
    } catch (NoSuchMethodException ex) {
      return "Failed to retrieve the cron expression value";
    }
  }
}
