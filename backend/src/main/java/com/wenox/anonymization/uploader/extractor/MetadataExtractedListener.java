package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.uploader.extractor.event.MetadataExtractedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MetadataExtractedListener {

  @EventListener
  public void onMetadataExtracted(MetadataExtractedEvent event) {
    System.out.println("Ready to use -- todo: mail notification");
  }
}
