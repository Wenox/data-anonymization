package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.commons.domain.FileType;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
  void store(String fileName, FileType type, MultipartFile file) throws IOException;
}
