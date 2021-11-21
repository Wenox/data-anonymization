package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.core.dto.UserRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRequest dto) {
    return ResponseEntity.ok(authService.register(dto));
  }
}
