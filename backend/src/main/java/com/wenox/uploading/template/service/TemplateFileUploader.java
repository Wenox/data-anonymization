package com.wenox.uploading.template.service;

import com.wenox.storage.service.FileStorage;
import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.uploading.template.namegenerator.FileNameGenerator;
import com.wenox.storage.domain.FileData;
import com.wenox.storage.service.TemplateFileData;
import com.wenox.storage.service.TemplateDumpStorage;
import com.wenox.uploading.template.namegenerator.UuidFileNameGenerator;
import com.wenox.uploading.template.repository.FileRepository;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class TemplateFileUploader implements FileUploader {

  private final FileStorage fileStorage;
  private final FileNameGenerator fileNameGenerator;
  private final FileRepository fileRepository;

  public TemplateFileUploader(final TemplateDumpStorage templateDumpStorage,
                              final UuidFileNameGenerator uuidFileNameGenerator,
                              final FileRepository fileRepository) {
    this.fileStorage = templateDumpStorage;
    this.fileNameGenerator = uuidFileNameGenerator;
    this.fileRepository = fileRepository;
  }

  public FileEntity upload(final FileData fileData) throws IOException {
    TemplateFileData templateFileData = (TemplateFileData) fileData;
    templateFileData.setSavedFileName(fileNameGenerator.get());
    fileStorage.save(fileData);

    final var file = new FileEntity();
    file.setOriginalFileName(templateFileData.getOriginalFileName());
    file.setSavedFileName(templateFileData.getSavedFileName());
    file.setType(templateFileData.getFileType());
    return fileRepository.save(file);
  }
}
