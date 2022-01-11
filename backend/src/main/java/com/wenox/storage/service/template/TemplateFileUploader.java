package com.wenox.storage.service.template;

import com.wenox.storage.service.FileStorage;
import com.wenox.storage.domain.FileEntity;
import com.wenox.storage.service.FileUploader;
import com.wenox.storage.service.namegenerator.FileNameGenerator;
import com.wenox.storage.domain.FileData;
import com.wenox.storage.domain.TemplateFileData;
import com.wenox.storage.service.namegenerator.UuidFileNameGenerator;
import com.wenox.storage.repository.FileRepository;
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

  @Override
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
