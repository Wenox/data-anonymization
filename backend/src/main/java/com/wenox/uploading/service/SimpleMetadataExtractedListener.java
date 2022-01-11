package com.wenox.uploading.service;

import com.wenox.uploading.service.listeners.MetadataExtractedListener;
import com.wenox.uploading.service.listeners.events.MetadataExtractedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleMetadataExtractedListener implements MetadataExtractedListener {

  private static final Logger log = LoggerFactory.getLogger(SimpleMetadataExtractedListener.class);

  @EventListener
  public void onMetadataExtracted(MetadataExtractedEvent event) {
    log.info("Template is now ready to be used. Create worksheets with it! :)");
  }
}