package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.FullUserResponse;
import com.wenox.anonymization.core.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<FullUserResponse>> getAll() {
    return ResponseEntity.ok(userService.getAll().stream().map(FullUserResponse::from).collect(Collectors.toList()));
  }
}
