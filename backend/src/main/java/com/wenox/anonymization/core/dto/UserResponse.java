package com.wenox.anonymization.core.dto;

import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;

public class UserResponse {

  private String id;
  private String email;
  private boolean blocked;
  private Role role;

  public UserResponse(String id, String email, boolean blocked, Role role) {
    this.id = id;
    this.email = email;
    this.blocked = blocked;
    this.role = role;
  }

  public static UserResponse from(User user) {
    return new UserResponse(user.getUuid().toString(), user.getEmail(), user.isBlocked(), user.getRole());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
