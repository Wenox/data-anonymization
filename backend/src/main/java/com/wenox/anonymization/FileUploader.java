package com.wenox.anonymization;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploader {

  private final FileStorage fileStorage;
  private final FileNameGenerator fileNameGenerator;
  private final FileRepository fileRepository;

  public FileUploader(final FileStorage fileStorage, final FileNameGenerator fileNameGenerator,
                      final FileRepository fileRepository) {
    this.fileStorage = fileStorage;
    this.fileNameGenerator = fileNameGenerator;
    this.fileRepository = fileRepository;
  }

  public FileEntity upload(final MultipartFile multipartFile, final FileType type) throws IOException {
    final String savedFileName = fileNameGenerator.get();

    fileStorage.store(savedFileName, type, multipartFile);

    final var file = new FileEntity();
    file.setOriginalName(multipartFile.getOriginalFilename());
    file.setSavedName(savedFileName);
    file.setType(type);
    return fileRepository.save(file);
  }
}
