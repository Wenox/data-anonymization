package com.wenox.storage.service;

import com.wenox.storage.domain.FileData;
import com.wenox.storage.domain.FileEntity;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface FileUploader {

  FileEntity upload(FileData fileData) throws IOException;
}
