package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.commons.domain.FileType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileStorage implements FileStorage {

  public void store(final String fileName, final FileType type, final MultipartFile multipartFile) throws IOException {
    final var directory = Files.createDirectories(Path.of("E:/anon/data-anonymization/stored_files"));
    Files.copy(multipartFile.getInputStream(), directory.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
  }
}
