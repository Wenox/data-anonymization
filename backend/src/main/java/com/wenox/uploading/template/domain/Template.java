package com.wenox.uploading.template.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.users.domain.FileType;
import com.wenox.users.domain.User;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "templates")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Template {

  @Id
  private final String id = UUID.randomUUID().toString();

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Worksheet> worksheets = new ArrayList<>();

  @OneToOne
  private FileEntity templateFile;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TemplateStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private FileType type;

  @Type(type = "json")
  @Column(columnDefinition = "jsonb", name = "metadata")
  private TemplateMetadata metadata;

  @Column(name = "title")
  private String title;

  @Column(name = "database_name")
  private String databaseName;

  @Column(name = "description")
  private String description;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  public void addWorksheet(Worksheet worksheet) {
    worksheets.add(worksheet);
    worksheet.setTemplate(this);
  }

  public void removeWorksheet(Worksheet worksheet) {
    worksheets.remove(worksheet);
    worksheet.setTemplate(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Template template = (Template) o;
    return id.equals(template.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public List<Worksheet> getWorksheets() {
    return worksheets;
  }

  public void setWorksheets(List<Worksheet> worksheets) {
    this.worksheets = worksheets;
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

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public FileEntity getTemplateFile() {
    return templateFile;
  }

  public void setTemplateFile(FileEntity templateFile) {
    this.templateFile = templateFile;
  }

  public TemplateMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(TemplateMetadata metadata) {
    this.metadata = metadata;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TemplateStatus getStatus() {
    return status;
  }

  public void setStatus(TemplateStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
