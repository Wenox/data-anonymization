package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
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

  @Column(nullable = false)
  private String savedName;

  @Column(nullable = false)
  private String originalName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType type;

  public Long getId() {
    return id;
  }

  public String getSavedName() {
    return savedName;
  }

  public void setSavedName(String savedName) {
    this.savedName = savedName;
  }

  public String getOriginalName() {
    return originalName;
  }

  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }
}
