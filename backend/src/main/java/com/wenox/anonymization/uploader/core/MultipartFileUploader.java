package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.core.namegenerator.FileNameGenerator;
import com.wenox.anonymization.uploader.storage.FileData;
import com.wenox.anonymization.uploader.storage.FileStorage;
import com.wenox.anonymization.uploader.storage.TemplateFileData;
import com.wenox.anonymization.uploader.storage.TemplateFileStorage;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class MultipartFileUploader implements FileUploader {

  private final FileStorage fileStorage;
  private final FileNameGenerator fileNameGenerator;
  private final FileRepository fileRepository;

  public MultipartFileUploader(final TemplateFileStorage fileStorage,
                               final FileNameGenerator fileNameGenerator,
                               final FileRepository fileRepository) {
    this.fileStorage = fileStorage;
    this.fileNameGenerator = fileNameGenerator;
    this.fileRepository = fileRepository;
  }
  public FileEntity upload(final FileData fileData) throws IOException {
    TemplateFileData templateFileData = (TemplateFileData) fileData;
    templateFileData.setSavedFileName(fileNameGenerator.get());
    fileStorage.store(fileData);

    final var file = new FileEntity();
    file.setOriginalName(templateFileData.getFileDTO().getOriginalFileName());
    file.setSavedName(templateFileData.getSavedFileName());
    file.setType(templateFileData.getFileType());
    return fileRepository.save(file);
  }
}
