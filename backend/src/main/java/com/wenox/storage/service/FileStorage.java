package com.wenox.storage.service;

import com.wenox.storage.domain.FileData;
import java.io.IOException;

public interface FileStorage {

  void save(FileData fileData) throws IOException;

  byte[] retrieve(String savedFileName) throws IOException;
}
