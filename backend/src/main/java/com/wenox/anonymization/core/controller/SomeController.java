package com.wenox.anonymization.core.controller;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/some")
public class SomeController {

  @PostMapping("/user")
  @PreAuthorize("hasAuthority('USER')")
  public String tryUser() {
    return "user ok";
  }

  @PostMapping("/admin")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String tryAdmin() {
    return "admin ok";
  }

  @PostMapping("/none")
  public String tryNoRoles() {
    var key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    String secretString = Encoders.BASE64.encode(key.getEncoded());
    System.out.println("secret string: " + secretString);

    return "none ok";
  }
}
