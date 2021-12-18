package com.wenox.anonymization.core.dto;

import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;

public class UserResponse {

  private String email;
  private Role role;

  public UserResponse(String email, Role role) {
    this.email = email;
    this.role = role;
  }

  public static UserResponse from(User user) {
    return new UserResponse(user.getEmail(), user.getRole());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
