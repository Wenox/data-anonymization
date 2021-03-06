package com.wenox.infrastructure.service;

import com.wenox.users.domain.FileType;

public class DatabaseConnection {

  private FileType databaseType;
  private String databaseName;
  private String username;
  private String password;

  private DatabaseConnection(FileType databaseType, String databaseName, String username, String password) {
    this.databaseType = databaseType;
    this.databaseName = databaseName;
    this.username = username;
    this.password = password;
  }

  public static DatabaseConnection newPostgreSQLConnection(String databaseName) {
    return new DatabaseConnection(FileType.PSQL, databaseName, "postgres", "postgres");
  }

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
