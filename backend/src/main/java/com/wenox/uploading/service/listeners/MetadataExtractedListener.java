package com.wenox.uploading.service.listeners;

import com.wenox.uploading.service.listeners.events.MetadataExtractedEvent;

public interface MetadataExtractedListener {

  void onMetadataExtracted(MetadataExtractedEvent event);
}
