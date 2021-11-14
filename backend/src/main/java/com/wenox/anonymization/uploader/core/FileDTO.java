package com.wenox.anonymization.uploader.core;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

  private byte[] bytes;
  private String originalFileName;
  private String contentType;

  private FileDTO(byte[] bytes, String originalFileName, String contentType) {
    this.bytes = bytes;
    this.originalFileName = originalFileName;
    this.contentType = contentType;
  }

  public static FileDTO from(MultipartFile multipartFile) throws IOException {
    return new FileDTO(multipartFile.getBytes(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
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
}
