package com.wenox.users.controller;

import com.wenox.users.dto.ResetPasswordRequest;
import com.wenox.users.service.resetpassword.ResetPasswordService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reset-password")
public class ResetPasswordController {

  private final ResetPasswordService resetPasswordService;

  public ResetPasswordController(ResetPasswordService resetPasswordService) {
    this.resetPasswordService = resetPasswordService;
  }

  @PostMapping("/request-reset")
  public ResponseEntity<Void> resetPassword(@RequestParam("email") String email) {
    try {
      resetPasswordService.resetPassword(email);
    } catch (Exception ignored) {}
    return ResponseEntity.ok().build();
  }

  @GetMapping("/show-change-password-form")
  public ResponseEntity<String> showChangePasswordForm(@RequestParam("token") String token) {
    return ResponseEntity.ok(resetPasswordService.getChangePasswordPageForToken(token));
  }

  @PostMapping("/change-password")
  public ResponseEntity<String> changePassword(@Valid @RequestBody ResetPasswordRequest dto) {
    return ResponseEntity.ok(resetPasswordService.changePassword(dto));
  }
}
