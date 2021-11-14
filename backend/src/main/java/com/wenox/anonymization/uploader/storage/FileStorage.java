package com.wenox.anonymization.uploader.storage;

import java.io.IOException;

public interface FileStorage {
  void store(FileData fileData) throws IOException;
}
