package com.wenox.uploading.template.dto;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileDto {

  private byte[] bytes;
  private String originalFileName;
  private String contentType;

  private FileDto(byte[] bytes, String originalFileName, String contentType) {
    this.bytes = bytes;
    this.originalFileName = originalFileName;
    this.contentType = contentType;
  }

  public static FileDto from(MultipartFile multipartFile) throws IOException {
    return new FileDto(multipartFile.getBytes(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
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
