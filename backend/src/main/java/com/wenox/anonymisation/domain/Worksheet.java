package com.wenox.anonymisation.domain;

import com.wenox.uploading.template.domain.Template;
import com.wenox.users.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "worksheets")
public class Worksheet {

  @Id
  private final String id = UUID.randomUUID().toString();

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Template template;

  @OneToMany(mappedBy = "worksheet", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Operation> operations = new ArrayList<>();

  public void addOperation(Operation operation) {
    operations.add(operation);
    operation.setWorksheet(this);
  }

  public void removeOperation(Operation operation) {
    operations.remove(operation);
    operation.setWorksheet(null);
  }

  public List<Operation> getOperations() {
    return operations;
  }

  public void setOperations(List<Operation> operations) {
    this.operations = operations;
  }

  public String getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
