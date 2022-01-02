package com.wenox.uploading.template.service;

import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.uploading.template.namegenerator.FileNameGenerator;
import com.wenox.storage.domain.FileData;
import com.wenox.storage.service.FileStorage;
import com.wenox.uploading.storage.TemplateFileData;
import com.wenox.uploading.storage.TemplateFileStorage;
import com.wenox.uploading.template.repository.FileRepository;
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
    file.setOriginalFileName(templateFileData.getFileDTO().getOriginalFileName());
    file.setSavedFileName(templateFileData.getSavedFileName());
    file.setType(templateFileData.getFileType());
    return fileRepository.save(file);
  }
}
