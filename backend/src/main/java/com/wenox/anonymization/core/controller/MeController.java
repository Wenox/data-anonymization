package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.EditMyProfileDto;
import com.wenox.anonymization.core.dto.FullUserResponse;
import com.wenox.anonymization.core.dto.MeResponse;
import com.wenox.anonymization.core.dto.RemoveMyAccountDto;
import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.core.service.UserService;
import com.wenox.anonymization.core.service.removeaccount.RemoveAccountService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

  private final AuthService authService;
  private final UserService userService;
  private final RemoveAccountService removeAccountService;

  public MeController(AuthService authService, UserService userService, RemoveAccountService removeAccountService) {
    this.authService = authService;
    this.userService = userService;
    this.removeAccountService = removeAccountService;
  }

  @GetMapping("/api/v1/me")
  @PreAuthorize("hasAnyAuthority('UNVERIFIED_USER', 'VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<MeResponse> getMe(Authentication authentication) {
    return ResponseEntity.ok(MeResponse.from(authService.getMe(authentication)));
  }

  @GetMapping("/api/v1/me/profile")
  @PreAuthorize("hasAnyAuthority('UNVERIFIED_USER', 'VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<FullUserResponse> getMyProfile(Authentication authentication) {
    return ResponseEntity.ok(FullUserResponse.from(authService.getMe(authentication)));
  }

  @PutMapping("/api/v1/me/profile/edit")
  @PreAuthorize("hasAnyAuthority('UNVERIFIED_USER', 'VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<ApiResponse> editMyProfile(@Valid @RequestBody EditMyProfileDto dto, Authentication authentication) {
    return ResponseEntity.ok(userService.editMyProfile(dto, authentication));
  }

  @PutMapping("/api/v1/me/remove-account")
  @PreAuthorize("hasAnyAuthority('UNVERIFIED_USER', 'VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<ApiResponse> removeMyAccount(@Valid @RequestBody RemoveMyAccountDto dto, Authentication authentication) {
    return ResponseEntity.ok(removeAccountService.removeMyAccount(dto, authentication));
  }

  @PutMapping("/api/v1/me/restore-account")
  public ResponseEntity<String> restoreAccount(@RequestParam("token") String token) {
    return ResponseEntity.ok(removeAccountService.restoreMyAccount(token));
  }
}
