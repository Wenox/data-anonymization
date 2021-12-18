package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.ResetPasswordRequest;
import com.wenox.anonymization.core.service.resetpassword.ResetPasswordService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    final var result = resetPasswordService.getChangePasswordPageForToken(token);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/api/v1/change-password")
  public ResponseEntity<String> changePassword(@Valid @RequestBody ResetPasswordRequest dto) {
    return ResponseEntity.ok(resetPasswordService.changePassword(dto));
  }
}
