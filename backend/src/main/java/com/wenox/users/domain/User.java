package com.wenox.users.domain;

import com.wenox.uploading.template.domain.Template;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  private final String id = UUID.randomUUID().toString();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Template> templates = new ArrayList<>();

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "purpose")
  private String purpose;

  @Column(name = "status")
  private UserStatus status;

  @Column(name = "verified")
  private boolean verified;

  @Column(name = "marked_for_removal")
  private boolean markedForRemoval;

  @Column(name = "force_removal")
  private boolean forceRemoval;

  @Column(name = "removal_request_data")
  private LocalDateTime removalRequestedDate;

  @Column(name = "removed_date")
  private LocalDateTime removedDate;

  @Column(name = "blocked_date")
  private LocalDateTime blockedDate;

  @Column(name = "registered_date")
  private LocalDateTime registeredDate;

  @Column(name = "last_login_date")
  private LocalDateTime lastLoginDate;

  public void addTemplate(Template template) {
    templates.add(template);
    template.setUser(this);
  }

  public void removeTemplate(Template template) {
    templates.remove(template);
    template.setUser(null);
  }

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

  public List<Template> getTemplates() {
    return templates != null ? templates : new ArrayList<>();
  }

  public void setTemplates(List<Template> templates) {
    this.templates = templates;
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
