package com.wenox.processing.service.listeners;

import com.wenox.processing.service.listeners.events.MirrorReadyEvent;

public interface MirrorReadyListener {

  void onMirrorReadyEvent(MirrorReadyEvent event);
}
