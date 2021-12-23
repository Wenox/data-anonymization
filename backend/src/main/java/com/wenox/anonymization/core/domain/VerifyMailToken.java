package com.wenox.anonymization.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verify_mail_tokens")
public class VerifyMailToken {

  @Id
  private final UUID uuid = UUID.randomUUID();

  @OneToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private String token;

  private LocalDateTime expirationDate;

  public UUID getUuid() {
    return uuid;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expirationDate);
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDateTime expirationDate) {
    this.expirationDate = expirationDate;
  }
}
