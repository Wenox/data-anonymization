package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.storage.FileData;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface FileUploader {

  FileEntity upload(FileData fileData) throws IOException;
}
