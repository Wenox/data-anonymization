package com.wenox.anonymization.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
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
  private final String id = UUID.randomUUID().toString();

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private String firstName;

  private String lastName;

  private String purpose;

  private UserStatus status;

  private boolean verified;

  private boolean markedForRemoval;

  private boolean forceRemoval;

  private LocalDateTime removalRequestedDate;

  private LocalDateTime removedDate;

  private LocalDateTime blockedDate;

  private LocalDateTime registeredDate;

  private LocalDateTime lastLoginDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getId() {
    return id;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public boolean isMarkedForRemoval() {
    return markedForRemoval;
  }

  public void setMarkedForRemoval(boolean markedForRemoval) {
    this.markedForRemoval = markedForRemoval;
  }

  public boolean isForceRemoval() {
    return forceRemoval;
  }

  public void setForceRemoval(boolean forceRemoval) {
    this.forceRemoval = forceRemoval;
  }

  public LocalDateTime getRemovalRequestedDate() {
    return removalRequestedDate;
  }

  public void setRemovalRequestedDate(LocalDateTime removalRequestedDate) {
    this.removalRequestedDate = removalRequestedDate;
  }

  public LocalDateTime getRemovedDate() {
    return removedDate;
  }

  public void setRemovedDate(LocalDateTime removedDate) {
    this.removedDate = removedDate;
  }

  public LocalDateTime getBlockedDate() {
    return blockedDate;
  }

  public void setBlockedDate(LocalDateTime blockedDate) {
    this.blockedDate = blockedDate;
  }

  public LocalDateTime getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(LocalDateTime registeredDate) {
    this.registeredDate = registeredDate;
  }

  public LocalDateTime getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(LocalDateTime lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }
}
