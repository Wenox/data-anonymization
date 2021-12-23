package com.wenox.anonymization.core.controller;

import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.FullUserResponse;
import com.wenox.anonymization.core.dto.RegisterUserRequest;
import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.core.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final AuthService authService;

  public UserController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<FullUserResponse>> getAll() {
    return ResponseEntity.ok(userService.getAll().stream().map(FullUserResponse::from).collect(Collectors.toList()));
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserRequest dto) {
    return ResponseEntity.ok(authService.register(dto));
  }

  @PutMapping("/{id}/block")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse> blockUser(@PathVariable("id") String id) {
    return ResponseEntity.ok(userService.blockUser(id));
  }

  @PutMapping("/{id}/unblock")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse> unblockUser(@PathVariable("id") String id) {
    return ResponseEntity.ok(userService.unblockUser(id));
  }

  @PutMapping("/{id}/force-removal")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse> forceUserRemoval(@PathVariable("id") String id) {
    return ResponseEntity.ok(userService.forceUserRemoval(id));
  }
}
