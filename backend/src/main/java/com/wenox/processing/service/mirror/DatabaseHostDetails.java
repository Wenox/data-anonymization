package com.wenox.processing.service.mirror;

public interface DatabaseHostDetails {

  String getIpAddress();

  String getHostPort();

  Boolean isRunningOnCloud();
}
