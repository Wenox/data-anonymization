package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.uploader.core.FileUploader;
import com.wenox.anonymization.uploader.core.MetadataFileUploader;
import com.wenox.anonymization.uploader.core.WorksheetTemplateRepository;
import com.wenox.anonymization.uploader.extractor.event.MetadataExtractedEvent;
import com.wenox.anonymization.uploader.storage.MetadataFileData;
import java.io.IOException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MetadataExtractedListener {

  private final FileUploader fileUploader;
  private final WorksheetTemplateRepository repository;

  public MetadataExtractedListener(MetadataFileUploader fileUploader, WorksheetTemplateRepository repository) {
    this.fileUploader = fileUploader;
    this.repository = repository;
  }

  @EventListener
  public void onMetadataExtracted(MetadataExtractedEvent event) {

    final MetadataFileData fileData = new MetadataFileData();
    fileData.setMetadata(event.getMetadata());

    final var worksheetTemplate = event.getTemplate();

    try {
      final var metadataFile = fileUploader.upload(fileData);
      worksheetTemplate.setStatus("METADATA_UPLOAD_SUCCESS");
      worksheetTemplate.setMetadataFile(metadataFile);
      repository.save(worksheetTemplate);
    } catch (final IOException ex) {
      worksheetTemplate.setStatus("METADATA_UPLOAD_FAILURE");
      worksheetTemplate.setMetadataFile(null);
      repository.save(worksheetTemplate);
      ex.printStackTrace();
    }
  }
}
