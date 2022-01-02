package com.wenox.users.controller;

import com.wenox.infrastructure.security.service.RefreshTokenService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final RefreshTokenService refreshTokenService;

  public AuthController(RefreshTokenService refreshTokenService) {
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    refreshTokenService.generateTokens(request, response);
  }
}
