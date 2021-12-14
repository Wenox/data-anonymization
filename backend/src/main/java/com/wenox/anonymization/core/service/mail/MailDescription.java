package com.wenox.anonymization.core.service.mail;

public class MailDescription {

  private final String to;
  private final String subject;
  private final String text;

  public MailDescription(String to, String subject, String text) {
    this.to = to;
    this.subject = subject;
    this.text = text;
  }

  public String getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getText() {
    return text;
  }
}
