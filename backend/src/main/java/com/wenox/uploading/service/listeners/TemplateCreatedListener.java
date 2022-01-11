package com.wenox.uploading.service.listeners;

import com.wenox.uploading.service.listeners.events.TemplateCreatedEvent;

public interface TemplateCreatedListener {

  void onTemplateCreated(TemplateCreatedEvent event);
}
