package com.wenox.infrastructure.service.mail;

public class MailDescription {

  public MailDescription(String to, String subject, String text) {
    this.to = to;
    this.subject = subject;
    this.text = text;
  }

  private String to;
  private String subject;
  private String text;

  public String to() {
    return to;
  }

  public void to(String to) {
    this.to = to;
  }

  public String subject() {
    return subject;
  }

  public void subject(String subject) {
    this.subject = subject;
  }

  public String text() {
    return text;
  }

  public void text(String text) {
    this.text = text;
  }
}
