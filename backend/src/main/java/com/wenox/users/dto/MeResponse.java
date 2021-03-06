package com.wenox.users.dto;

import com.wenox.users.domain.Role;
import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import java.time.LocalDateTime;
import java.util.Objects;

public class MeResponse {

  public static MeResponse from(User user) {
    var dto = new MeResponse();
    dto.setId(user.getId());
    dto.setEmail(user.getEmail());
    dto.setRole(user.getRole());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setStatus(user.getStatus());
    dto.setVerified(user.isVerified());
    dto.setMarkedForRemoval(user.isMarkedForRemoval());
    dto.setRemovalRequestedDate(user.getRemovalRequestedDate());
    dto.setBlockedDate(user.getBlockedDate());
    return dto;
  }

  private String id;

  private String email;

  private Role role;

  private String firstName;

  private String lastName;

  private UserStatus status;

  private boolean verified;

  private boolean markedForRemoval;

  private LocalDateTime removalRequestedDate;

  private LocalDateTime blockedDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeResponse user = (MeResponse) o;
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

  public LocalDateTime getRemovalRequestedDate() {
    return removalRequestedDate;
  }

  public void setRemovalRequestedDate(LocalDateTime removalRequestedDate) {
    this.removalRequestedDate = removalRequestedDate;
  }

  public LocalDateTime getBlockedDate() {
    return blockedDate;
  }

  public void setBlockedDate(LocalDateTime blockedDate) {
    this.blockedDate = blockedDate;
  }
}

