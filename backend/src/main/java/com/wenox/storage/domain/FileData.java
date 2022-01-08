package com.wenox.storage.domain;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileData {
  private String savedFileName;
  private byte[] bytes;
  private String originalFileName;
  private String contentType;

  protected FileData(FileData fileData) {
    this.savedFileName = fileData.getSavedFileName();
    this.bytes = fileData.getBytes();
    this.originalFileName = fileData.getOriginalFileName();
    this.contentType = fileData.getContentType();
  }

  private FileData() {

  }

  public static FileData from(MultipartFile multipartFile) throws IOException {
    var fileData = new FileData();
    fileData.setSavedFileName(null);
    fileData.setBytes(multipartFile.getBytes());
    fileData.setOriginalFileName(multipartFile.getOriginalFilename());
    fileData.setContentType(multipartFile.getContentType());
    return fileData;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getSavedFileName() {
    return savedFileName;
  }

  public void setSavedFileName(String savedFileName) {
    this.savedFileName = savedFileName;
  }
}

