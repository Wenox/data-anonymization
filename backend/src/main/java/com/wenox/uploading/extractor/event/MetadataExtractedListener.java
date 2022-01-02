package com.wenox.uploading.extractor.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MetadataExtractedListener {

  @EventListener
  public void onMetadataExtracted(MetadataExtractedEvent event) {
    System.out.println("Ready to use -- todo: mail notification");
  }
}
