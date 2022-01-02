package com.wenox.infrastructure.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RefreshTokenService {

  void generateTokens(HttpServletRequest request, HttpServletResponse response);
}
