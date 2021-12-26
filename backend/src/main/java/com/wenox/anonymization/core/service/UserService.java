package com.wenox.anonymization.core.service;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import com.wenox.anonymization.core.dto.ApiResponse;
import com.wenox.anonymization.core.dto.EditMyProfileDto;
import com.wenox.anonymization.core.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final AuthService authService;

  public UserService(UserRepository userRepository,
                     AuthService authService) {
    this.userRepository = userRepository;
    this.authService = authService;
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public ApiResponse blockUser(String id) {
    var user = userRepository.findById(id).orElseThrow();
    if (user.getStatus() == UserStatus.REMOVED) {
      return ApiResponse.ofError("Removed user cannot be blocked.");
    }
    if (user.getStatus() == UserStatus.BLOCKED) {
      return ApiResponse.ofError("This user had already been blocked.");
    }
    user.setStatus(UserStatus.BLOCKED);
    user.setBlockedDate(LocalDateTime.now());
    userRepository.save(user);
    return ApiResponse.ofSuccess("Successfully blocked the user.");
  }

  public ApiResponse unblockUser(String id) {
    var user = userRepository.findById(id).orElseThrow();
    if (user.getStatus() == UserStatus.REMOVED) {
      return ApiResponse.ofError("Removed user cannot be unblocked.");
    }
    user.setStatus(UserStatus.ACTIVE);
    userRepository.save(user);
    return ApiResponse.ofSuccess("Successfully unblocked the user.");
  }

  public ApiResponse forceUserRemoval(String id) {
    var user = userRepository.findById(id).orElseThrow();
    if (user.getStatus() == UserStatus.REMOVED) {
      return ApiResponse.ofError("This user account had been removed.");
    }
    user.setForceRemoval(true);
    userRepository.save(user);
    return ApiResponse.ofSuccess("Successfully forced the user account to be removed.");
  }

  public ApiResponse editMyProfile(EditMyProfileDto dto, Authentication auth) {
    User me = authService.getMe(auth);
    me.setEmail(dto.getEmail());
    me.setFirstName(dto.getFirstName());
    me.setLastName(dto.getLastName());
    me.setPurpose(dto.getPurpose());
    userRepository.save(me);
    return ApiResponse.ofSuccess("Profile updated successfully.");
  }
}
