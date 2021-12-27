package com.wenox.anonymization.core.service.task;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.TaskDto;
import com.wenox.anonymization.core.service.task.cronservice.CronService;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final Environment env;
  private final List<CronService> cronJobs;

  public TaskService(Environment env,
                     List<CronService> cronJobs) {
    this.env = env;
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

    if (cronService.isExecutable()) {
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
    dto.setCronExpression(cronService.getCronExpression(env));
    dto.setTaskName(cronService.getTaskName());
    dto.setDescription(cronService.getDescription());
    dto.setScheduled(cronService.isScheduled());
    dto.setExecutable(cronService.isExecutable());
    return dto;
  }
}
