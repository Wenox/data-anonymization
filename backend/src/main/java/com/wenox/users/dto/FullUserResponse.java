package com.wenox.users.dto;

import com.wenox.users.domain.Role;
import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import com.wenox.users.service.FormatDate;
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
    dto.setStatus(user.getStatus());
    dto.setVerified(user.isVerified());
    dto.setMarkedForRemoval(user.isMarkedForRemoval());
    dto.setForceRemoval(user.isForceRemoval());
    dto.setRemovalRequestedDate(FormatDate.toString(user.getRemovalRequestedDate()));
    dto.setRemovedDate(FormatDate.toString(user.getRemovedDate()));
    dto.setBlockedDate(FormatDate.toString(user.getBlockedDate()));
    dto.setRegisteredDate(FormatDate.toString(user.getRegisteredDate()));
    dto.setLastLoginDate(FormatDate.toString(user.getLastLoginDate()));
    return dto;
  }

  private String id;

  private String email;

  private Role role;

  private String firstName;

  private String lastName;

  private String purpose;

  private UserStatus status;

  private boolean verified;

  private boolean markedForRemoval;

  private boolean forceRemoval;

  private String removalRequestedDate;

  private String removedDate;

  private String blockedDate;

  private String registeredDate;

  private String lastLoginDate;

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

  public String getRemovalRequestedDate() {
    return removalRequestedDate;
  }

  public void setRemovalRequestedDate(String removalRequestedDate) {
    this.removalRequestedDate = removalRequestedDate;
  }

  public String getRemovedDate() {
    return removedDate;
  }

  public void setRemovedDate(String removedDate) {
    this.removedDate = removedDate;
  }

  public String getBlockedDate() {
    return blockedDate;
  }

  public void setBlockedDate(String blockedDate) {
    this.blockedDate = blockedDate;
  }

  public String getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(String registeredDate) {
    this.registeredDate = registeredDate;
  }

  public String getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(String lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }
}

