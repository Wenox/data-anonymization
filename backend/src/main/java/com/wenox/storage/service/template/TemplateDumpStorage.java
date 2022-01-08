package com.wenox.storage.service.template;

import com.wenox.storage.service.LocalFileStorage;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TemplateDumpStorage extends LocalFileStorage {

  @Value("${uploader.templates.path}")
  private String templatesPath;

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(templatesPath);
  }
}
