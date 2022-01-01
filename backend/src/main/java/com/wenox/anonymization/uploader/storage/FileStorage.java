package com.wenox.anonymization.uploader.storage;

import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface FileStorage {

  void store(FileData fileData) throws IOException;
  byte[] retrieve(String savedFileName) throws IOException;
}
