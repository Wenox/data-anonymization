package com.wenox.anonymization.core.domain;

import com.wenox.anonymization.core.domain.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  private final UUID uuid = UUID.randomUUID();

  private String email;

  private String password;

  private Boolean blocked;

  @Enumerated(EnumType.STRING)
  private Role role;

  private LocalDateTime lastLogin;

  public UUID getUuid() {
    return uuid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(Boolean blocked) {
    this.blocked = blocked;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(com.wenox.anonymization.core.domain.Role role) {
    this.role = role;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }
}
