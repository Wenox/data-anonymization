package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.service.resetpassword.ResetPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController {

  private final ResetPasswordService resetPasswordService;

  public ResetPasswordController(ResetPasswordService resetPasswordService) {
    this.resetPasswordService = resetPasswordService;
  }

  @PostMapping("/api/v1/request-reset-password")
  public ResponseEntity<Void> resetPassword(@RequestParam("email") String email) {
    try {
      resetPasswordService.resetPassword(email);
    } catch (Exception ignored) {}
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/v1/show-change-password")
  public ResponseEntity<String> showChangePassword(@RequestParam("token") String token) {
    System.out.println("Inside endpoint, token: " + token);
    final var result = resetPasswordService.getChangePasswordPageForToken(token);
    System.out.println("Result: " + result);
    return ResponseEntity.ok(result);
  }
}
