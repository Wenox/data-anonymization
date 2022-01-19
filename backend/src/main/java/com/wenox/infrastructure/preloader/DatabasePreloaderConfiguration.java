package com.wenox.infrastructure.preloader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabasePreloaderConfiguration {

  private final String adminLogin;
  private final String adminPassword;

  public DatabasePreloaderConfiguration(@Value("${core.preloader.admin.login}") String adminLogin,
                                        @Value("${core.preloader.admin.password}") String adminPassword) {
    this.adminLogin = adminLogin;
    this.adminPassword = adminPassword;
  }

  public String getAdminLogin() {
    return adminLogin;
  }

  public String getAdminPassword() {
    return adminPassword;
  }
}
