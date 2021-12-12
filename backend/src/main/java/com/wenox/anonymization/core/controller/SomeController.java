package com.wenox.anonymization.core.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/some")
public class SomeController {

  @PostMapping("/user")
  @PreAuthorize("hasAuthority('USER')")
  public String tryUser(Authentication authentication) {
    final var userDetails1 = (UserDetails) authentication.getPrincipal();
    System.out.println("username: " + userDetails1.getUsername());

    return "user ok";
  }

  @PostMapping("/admin")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String tryAdmin() {
    return "admin ok";
  }

  @PostMapping("/none")
  public String tryNoRoles() {
    return "none ok";
  }
}
