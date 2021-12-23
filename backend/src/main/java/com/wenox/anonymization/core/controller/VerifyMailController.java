package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.service.verifymail.VerifyMailService;
import org.springframework.http.ResponseEntity;
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
}
