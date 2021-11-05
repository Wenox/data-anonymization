package com.wenox.anonymization;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
  void store(String fileName, FileType type, MultipartFile file) throws IOException;
}
