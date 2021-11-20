package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.core.namegenerator.FileNameGenerator;
import com.wenox.anonymization.uploader.storage.FileData;
import com.wenox.anonymization.uploader.storage.FileStorage;
import com.wenox.anonymization.uploader.storage.MetadataFileData;
import com.wenox.anonymization.uploader.storage.MetadataFileStorage;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Deprecated
@Service
public class MetadataFileUploader implements FileUploader {

  private final FileStorage fileStorage;
  private final FileNameGenerator fileNameGenerator;
  private final FileRepository fileRepository;

  public MetadataFileUploader(final FileNameGenerator fileNameGenerator,
                              final FileRepository fileRepository) {
    this.fileStorage = new MetadataFileStorage();
    this.fileNameGenerator = fileNameGenerator;
    this.fileRepository = fileRepository;
  }
  public FileEntity upload(final FileData fileData) throws IOException {
    MetadataFileData metadataFileData = (MetadataFileData) fileData;
    metadataFileData.setSavedFileName(fileNameGenerator.get());
    fileStorage.store(fileData);

    final var file = new FileEntity();
    file.setOriginalName(null);
    file.setSavedName(metadataFileData.getSavedFileName());
    file.setType(null); // todo: consider not null
    return fileRepository.save(file);
  }
}
