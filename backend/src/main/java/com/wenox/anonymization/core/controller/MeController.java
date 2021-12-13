package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.UserResponse;
import com.wenox.anonymization.core.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MeController {

  private final AuthService authService;

  public MeController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping(value = "/me")
  public ResponseEntity<UserResponse> getMe(Authentication authentication) {
    return ResponseEntity.ok(authService.getMe(authentication));
  }

  @GetMapping(value = "/me2")
  public ResponseEntity<UserResponse> getMe2(HttpServletRequest request) {
    return ResponseEntity.ok(authService.getMe2(request));
  }
}
