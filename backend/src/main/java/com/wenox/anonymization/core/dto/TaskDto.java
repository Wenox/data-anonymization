package com.wenox.anonymization.core.dto;

public class TaskDto {
  private String cronExpression;
  private String taskName;
  private String description;
  private boolean isScheduled;
  private boolean isExecutable;

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
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
}
