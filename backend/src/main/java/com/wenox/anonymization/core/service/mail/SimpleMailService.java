package com.wenox.anonymization.core.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailService implements MailService {

  private static final Logger log = LoggerFactory.getLogger(SimpleMailService.class);

  private final JavaMailSender emailSender;

  public SimpleMailService(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  public void sendMail(MailDescription description) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom("data.anonymisation@gmail.com");
    simpleMailMessage.setTo(description.getTo());
    simpleMailMessage.setSubject(description.getSubject());
    simpleMailMessage.setText(description.getText());

    try {
      emailSender.send(simpleMailMessage);
      log.info("Successfully sent mail to {}.", description.getTo());
    } catch (Exception ex) {
      log.error("Failed to send mail '{}' to {} due to {}.", description.getSubject(), description.getTo(), ex.getMessage());
    }
  }
}
