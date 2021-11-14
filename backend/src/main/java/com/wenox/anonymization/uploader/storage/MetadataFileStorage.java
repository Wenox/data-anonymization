package com.wenox.anonymization.uploader.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class MetadataFileStorage implements FileStorage {

  public void store(FileData fileData) {
    MetadataFileData metadataFileData = (MetadataFileData) fileData;

    try {
      new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("E:/anon/data-anonymization/stored_files/" + metadataFileData.getSavedFileName()), metadataFileData.getMetadata());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
