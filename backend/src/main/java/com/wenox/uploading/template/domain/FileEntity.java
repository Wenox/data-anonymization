package com.wenox.uploading.template.domain;

import com.wenox.users.domain.FileType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "saved_file_name", nullable = false)
  private String savedFileName;

  @Column(name = "original_file_name")
  private String originalFileName;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = true)
  private FileType type;

  public Long getId() {
    return id;
  }

  public String getSavedFileName() {
    return savedFileName;
  }

  public void setSavedFileName(String savedFileName) {
    this.savedFileName = savedFileName;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }
}
