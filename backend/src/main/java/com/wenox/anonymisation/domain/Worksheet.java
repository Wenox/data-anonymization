package com.wenox.anonymisation.domain;

import com.wenox.processing.domain.Outcome;
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

  @OneToMany(mappedBy = "worksheet", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Outcome> outcomes = new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER)
  private Template template;

  @OneToMany(mappedBy = "worksheet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ColumnOperations> listOfColumnOperations = new ArrayList<>();

  public void addColumnOperations(ColumnOperations operation) {
    listOfColumnOperations.add(operation);
    operation.setWorksheet(this);
  }

  public void removeColumnOperations(ColumnOperations operation) {
    listOfColumnOperations.remove(operation);
    operation.setWorksheet(null);
  }

  public List<Outcome> getOutcomes() {
    return outcomes;
  }

  public void setOutcomes(List<Outcome> outcomes) {
    this.outcomes = outcomes;
  }

  public List<ColumnOperations> getListOfColumnOperations() {
    return listOfColumnOperations;
  }

  public void setListOfColumnOperations(List<ColumnOperations> operations) {
    this.listOfColumnOperations = operations;
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
