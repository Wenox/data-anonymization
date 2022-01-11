package com.wenox.uploading.service.listeners;

import com.wenox.uploading.service.listeners.events.TemplateStoredEvent;

public interface TemplateStoredListener {

  void onTemplateStored(TemplateStoredEvent event);
}
