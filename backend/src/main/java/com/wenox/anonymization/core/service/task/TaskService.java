package com.wenox.anonymization.core.service.task;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.TaskDto;
import com.wenox.anonymization.core.service.task.cronservice.CronService;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final List<CronService> cronJobs;

  public TaskService(List<CronService> cronJobs) {
    this.cronJobs = cronJobs;
  }

  public List<TaskDto> getAll() {
    return cronJobs.stream().map(this::toDto).toList();
  }

  public ApiResponse execute(String task) {
    final var cronService = cronJobs
        .stream()
        .filter(job -> task.equals(job.getTaskName()))
        .findFirst()
        .orElseThrow();

    if (!cronService.isExecutable()) {
      return ApiResponse.ofError("This task cannot be manually executed.");
    }

    try {
      cronService.execute();
    } catch (Exception ex) {
      return ApiResponse.ofError("Task execution has finished with an error.");
    }
    return ApiResponse.ofSuccess("Task execution has finished successfully.");
  }

  private TaskDto toDto(CronService cronService) {
    final var dto = new TaskDto();
    dto.setCronExpression(cronService.getCronExpression());
    dto.setTaskName(cronService.getTaskName());
    dto.setDescription(cronService.getDescription());
    dto.setScheduled(cronService.isScheduled());
    dto.setExecutable(cronService.isExecutable());
    cronService
        .nextScheduledExecution()
        .ifPresentOrElse(
            (value) -> dto.setNextScheduledExecution(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(value)),
            () -> dto.setNextScheduledExecution(null)
        );
    return dto;
  }
}
