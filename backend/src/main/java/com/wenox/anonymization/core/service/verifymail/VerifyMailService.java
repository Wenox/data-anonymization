package com.wenox.anonymization.core.service.verifymail;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.VerifyMailToken;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.service.mail.MailDescription;
import com.wenox.anonymization.core.service.mail.MailService;
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

  public void sendVerificationMailToUser(User user) {
    VerifyMailToken token = tokenService.generateTokenForUser(user);
    System.out.println("Verify your account: http://localhost:3000/verify-account?token=" + token.getToken());
    Executors.newSingleThreadExecutor()
        .execute(() -> mailService.sendMail(new MailDescription(user.getEmail(), "Verify your account",
            "Verify your account: http://localhost:3000/verify-account?token=" + token.getToken())));
  }

  public String verify(String token) {
    VerifyMailToken tokenEntity = tokenService.findByToken(token);
    if (tokenEntity == null) {
      return "invalid-token";
    }
    User user = tokenEntity.getUser();
    if (user.isVerified()) {
      return "already-verified";
    }
    if (tokenEntity.isExpired()) {
      return "expired-token";
    }
    user.setVerified(true);
    userRepository.save(user);
    return "success";
  }
}
