package com.wenox.anonymization.uploader.core.namegenerator;

import com.wenox.anonymization.uploader.core.namegenerator.FileNameGenerator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UuidFileNameGenerator implements FileNameGenerator {

  public String get() {
    return StringUtils.cleanPath(UUID.randomUUID().toString());
  }
}
