package com.wenox.infrastructure.security.service;

import org.springframework.security.core.userdetails.User;

public class UserJwt {

  private final String username;
  private final String role;

  public UserJwt(String username, String role) {
    this.username = username;
    this.role = role;
  }

  public static UserJwt from(User user) {
    return new UserJwt(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }
}
