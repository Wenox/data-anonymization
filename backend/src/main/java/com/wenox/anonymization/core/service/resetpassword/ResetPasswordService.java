package com.wenox.anonymization.core.service.resetpassword;

import com.wenox.anonymization.core.domain.ResetPasswordToken;
import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.dto.ResetPasswordRequest;
import com.wenox.anonymization.core.repository.ResetPasswordTokenRepository;
import com.wenox.anonymization.core.repository.UserRepository;
import com.wenox.anonymization.core.service.mail.MailDescription;
import com.wenox.anonymization.core.service.mail.SimpleMailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordService {

  private final UserRepository userRepository;
  private final SimpleMailService mailService;
  private final ResetPasswordTokenRepository resetPasswordTokenRepository;
  private final ResetPasswordTokenService resetPasswordTokenService;
  private final PasswordEncoder passwordEncoder;

  public ResetPasswordService(UserRepository userRepository,
                              SimpleMailService mailService,
                              ResetPasswordTokenRepository resetPasswordTokenRepository,
                              ResetPasswordTokenService resetPasswordTokenService,
                              PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.mailService = mailService;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.resetPasswordTokenService = resetPasswordTokenService;
    this.passwordEncoder = passwordEncoder;
  }

  public void resetPassword(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();

    ResetPasswordToken token = resetPasswordTokenRepository.save(resetPasswordTokenService.generateTokenForUser(user));

    System.out.println("Change your password: http://localhost:3000/change-password?token=" + token.getToken());
    mailService.sendMail(new MailDescription
        (user.getEmail(),
            "Reset your password",
            "Change your password: http://localhost:3000/change-password?token=" + token.getToken()));
  }

  public String getChangePasswordPageForToken(String token) {
    ResetPasswordToken tokenEntity = resetPasswordTokenRepository.findByToken(token);
    if (tokenEntity == null) {
      return "invalid-token";
    }
    if (tokenEntity.isExpired()) {
      return "expired-token";
    }
    if (tokenEntity.isConsumed()) {
      return "consumed-token";
    }
    return "form";
  }

  @Transactional
  public String changePassword(ResetPasswordRequest dto) {
    ResetPasswordToken tokenEntity = resetPasswordTokenRepository.findByToken(dto.getToken());
    if (tokenEntity == null) {
      return "invalid-token";
    }
    if (tokenEntity.isExpired()) {
      return "expired-token";
    }
    if (tokenEntity.isConsumed()) {
      return "consumed-token";
    }

    User user = tokenEntity.getUser();
    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(user);

    tokenEntity.setConsumed(true);
    resetPasswordTokenRepository.save(tokenEntity);

    return "success";
  }
}
