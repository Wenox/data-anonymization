package com.wenox.anonymization.uploader.extractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MetadataExtractedListener {

  @EventListener
  public void onMetadataExtracted(MetadataExtractedEvent event) {

    try {
      new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("E:/anon/data-anonymization/out2.json"), event.getMetadata());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
