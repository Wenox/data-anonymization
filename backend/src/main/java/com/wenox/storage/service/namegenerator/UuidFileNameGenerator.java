package com.wenox.storage.service.namegenerator;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UuidFileNameGenerator implements FileNameGenerator {

  @Override
  public String get() {
    return StringUtils.cleanPath(UUID.randomUUID().toString());
  }
}
