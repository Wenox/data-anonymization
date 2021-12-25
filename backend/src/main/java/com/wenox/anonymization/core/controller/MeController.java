package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.FullUserResponse;
import com.wenox.anonymization.core.dto.MeResponse;
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
  public ResponseEntity<MeResponse> getMe(Authentication authentication) {
    return ResponseEntity.ok(MeResponse.from(authService.getMe(authentication)));
  }

  @GetMapping("/api/v1/me/profile")
  public ResponseEntity<FullUserResponse> getMyProfile(Authentication authentication) {
    var response = FullUserResponse.from(authService.getMe(authentication));
    System.out.println("response: " + response.getEmail());
    return ResponseEntity.ok(response);
  }
}
