package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.UserResponse;
import com.wenox.anonymization.core.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

  private final AuthService authService;

  public MeController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/api/v1/me")
  public ResponseEntity<UserResponse> getMe(Authentication authentication) {
    return ResponseEntity.ok(authService.getMe(authentication));
  }
}
