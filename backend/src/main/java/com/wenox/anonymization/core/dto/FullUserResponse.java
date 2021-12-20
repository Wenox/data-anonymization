package com.wenox.anonymization.core.dto;

import com.wenox.anonymization.core.domain.Role;
import com.wenox.anonymization.core.domain.User;
import java.time.LocalDateTime;
import java.util.Objects;

/** Password and relations are not returned. */
public class FullUserResponse {

  public static FullUserResponse from(User user) {
    var dto = new FullUserResponse();
    dto.setId(user.getId());
    dto.setEmail(user.getEmail());
    dto.setRole(user.getRole());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setPurpose(user.getPurpose());
    dto.setBlocked(user.isBlocked());
    dto.setVerified(user.isVerified());
    dto.setMarkedForRemoval(user.isMarkedForRemoval());
    dto.setForceRemoval(user.isForceRemoval());
    dto.setRemovalRequestedDate(user.getRemovalRequestedDate());
    dto.setRemovedDate(user.getRemovedDate());
    dto.setBlockedDate(user.getBlockedDate());
    dto.setRegisteredDate(user.getRegisteredDate());
    dto.setLastLoginDate(user.getLastLoginDate());
    return dto;
  }

  private String id;

  private String email;

  private Role role;

  private String firstName;

  private String lastName;

  private String purpose;

  private boolean blocked;

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
    FullUserResponse user = (FullUserResponse) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
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

