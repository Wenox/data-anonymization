package com.wenox.anonymization.core.service.resetpassword;

import com.wenox.anonymization.core.domain.ResetPasswordToken;
import com.wenox.anonymization.core.domain.User;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {

  @Value("${core.resetPasswordToken.expireTimeInSeconds}")
  private Long tokenExpireTime;

  public ResetPasswordToken generateTokenForUser(User user) {
    ResetPasswordToken token = new ResetPasswordToken();
    token.setUser(user);
    token.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpireTime));
    token.setToken(UUID.randomUUID().toString());
    return token;
  }
}
