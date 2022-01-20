package com.wenox.users.service.verifymail;

import com.wenox.users.domain.Role;
import com.wenox.users.domain.User;
import com.wenox.users.domain.UserStatus;
import com.wenox.users.domain.VerifyMailToken;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.repository.UserRepository;
import com.wenox.infrastructure.service.mail.MailDescription;
import com.wenox.infrastructure.service.mail.MailService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

@Service
public class VerifyMailService {

  private final VerifyMailTokenService tokenService;
  private final MailService mailService;
  private final UserRepository userRepository;

  public VerifyMailService(VerifyMailTokenService tokenService,
                           MailService mailService,
                           UserRepository userRepository) {
    this.tokenService = tokenService;
    this.mailService = mailService;
    this.userRepository = userRepository;
  }

  public ApiResponse confirmVerification(String id) {
    User user = userRepository.findById(id).orElseThrow();
    if (user.isVerified()) {
      return ApiResponse.ofError("This user is already verified.");
    }
    if (user.getStatus() == UserStatus.REMOVED || user.isForceRemoval()) {
      return ApiResponse.ofError("This user cannot be verified.");
    }
    user.setVerified(true);
    user.setRole(Role.VERIFIED_USER);
    userRepository.save(user);
    return ApiResponse.ofSuccess("Successfully confirmed user's verification.");
  }

  public void sendVerificationMailToUser(User user) {
    VerifyMailToken token = tokenService.generateTokenForUser(user);
    sendVerificationMail(token);
  }

  public String verify(String token) {
    VerifyMailToken tokenEntity = tokenService.findByToken(token);
    if (tokenEntity == null) {
      return "invalid-token";
    }
    User user = tokenEntity.getUser();
    if (user.getStatus() == UserStatus.REMOVED || user.isForceRemoval()) {
      return "invalid-token";
    }
    if (user.isVerified()) {
      return "already-verified";
    }
    if (tokenEntity.isExpired()) {
      return "expired-token";
    }
    user.setVerified(true);
    user.setRole(Role.VERIFIED_USER);
    userRepository.save(user);
    return "success";
  }

  public String resendGivenToken(String token) {
    VerifyMailToken tokenEntity = tokenService.findByToken(token);
    if (tokenEntity == null) {
      return "invalid-token";
    }

    User user = tokenEntity.getUser();
    if (user.getStatus() == UserStatus.REMOVED || user.isForceRemoval()) {
      return "invalid-token";
    }
    if (user.isVerified()) {
      return "already-verified";
    }

    sendVerificationMail(tokenService.renewTokenExpirationTime(tokenEntity));
    return "token-sent-again";
  }

  public String resendGivenEmail(String email) {
    VerifyMailToken tokenEntity = tokenService.findByUserEmail(email);
    if (tokenEntity == null) {
      return "invalid-token";
    }

    User user = tokenEntity.getUser();
    if (user.isVerified()) {
      return "already-verified";
    }

    sendVerificationMail(tokenService.renewTokenExpirationTime(tokenEntity));
    return "token-sent-again";
  }

  public void sendVerificationMail(VerifyMailToken token) {
    System.out.println("Verify your account: http://localhost:3000/verify-mail?token=" + token.getToken());
    Executors.newSingleThreadExecutor()
        .execute(() -> mailService.sendMail(new MailDescription(token.getUser().getEmail(), "Verify your account",
            "Verify your account: http://localhost:3000/verify-mail?token=" + token.getToken())));
  }
}
