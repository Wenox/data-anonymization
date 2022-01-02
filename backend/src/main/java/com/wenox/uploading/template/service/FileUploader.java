package com.wenox.uploading.template.service;

import com.wenox.storage.domain.FileData;
import com.wenox.uploading.template.domain.FileEntity;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface FileUploader {

  FileEntity upload(FileData fileData) throws IOException;
}
