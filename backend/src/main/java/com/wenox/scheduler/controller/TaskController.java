package com.wenox.scheduler.controller;

import com.wenox.scheduler.dto.TaskDto;
import com.wenox.users.dto.ApiResponse;
import com.wenox.scheduler.service.task.TaskService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<TaskDto>> getSchedulers() {
    return ResponseEntity.ok(taskService.getAll());
  }

  @PostMapping("/{task}/execute")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse> execute(@PathVariable("task") String task) {
    return ResponseEntity.ok(taskService.execute(task));
  }
}
