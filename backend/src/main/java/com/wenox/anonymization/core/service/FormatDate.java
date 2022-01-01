package com.wenox.anonymization.core.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class FormatDate {

  public static String toString(LocalDateTime date) {
    return Optional.ofNullable(date)
        .map(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)::format)
        .orElse("");
  }
}
