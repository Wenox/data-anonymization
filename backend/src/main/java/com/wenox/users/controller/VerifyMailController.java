package com.wenox.users.controller;

import com.wenox.users.dto.ApiResponse;
import com.wenox.users.service.verifymail.VerifyMailService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyMailController {

  private final VerifyMailService verifyMailService;

  public VerifyMailController(VerifyMailService verifyMailService) {
    this.verifyMailService = verifyMailService;
  }

  @PostMapping("/api/v1/users/verify-mail")
  public ResponseEntity<String> verify(@RequestParam("token") String token) {
    return ResponseEntity.ok(verifyMailService.verify(token));
  }

  @PostMapping("/api/v1/users/verify-mail/send-again")
  public ResponseEntity<String> resend(@RequestParam(value = "token", required = false) String token,
                                       @RequestParam(value = "email", required = false) String email) {
    return Optional.ofNullable(token)
        .map(v -> ResponseEntity.ok(verifyMailService.resendGivenToken(v)))
        .orElse(ResponseEntity.ok(verifyMailService.resendGivenEmail(email)));

  }

  @PostMapping("/api/v1/users/{id}/confirm-mail-verification")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse> confirmVerification(@PathVariable("id") String id) {
    return ResponseEntity.ok(verifyMailService.confirmVerification(id));
  }
}
