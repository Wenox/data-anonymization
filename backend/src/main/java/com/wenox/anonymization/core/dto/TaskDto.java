package com.wenox.anonymization.core.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDto {
  private String taskName;
  private String cronExpression;
  private String description;
  private boolean isScheduled;
  private boolean isExecutable;
  private String nextScheduledExecution;

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isScheduled() {
    return isScheduled;
  }

  public void setScheduled(boolean scheduled) {
    isScheduled = scheduled;
  }

  public boolean isExecutable() {
    return isExecutable;
  }

  public void setExecutable(boolean executable) {
    isExecutable = executable;
  }

  public String getNextScheduledExecution() {
    return nextScheduledExecution;
  }

  public void setNextScheduledExecution(String nextExecution) {
    this.nextScheduledExecution = nextExecution;
  }
}
