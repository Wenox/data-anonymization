package com.wenox.infrastructure.service;

import com.wenox.users.domain.FileType;

public class ConnectionDetails {

  private FileType databaseType;
  private String databaseName;
  private String username;
  private String password;

  public FileType getDatabaseType() {
    return databaseType;
  }

  public void setDatabaseType(FileType databaseType) {
    this.databaseType = databaseType;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
