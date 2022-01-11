package com.wenox.users.service.resetpassword;

import com.wenox.users.domain.ResetPasswordToken;
import com.wenox.users.domain.User;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {

  private final Long tokenExpireTime;

  public ResetPasswordTokenService(@Value("${core.resetPasswordToken.expireTimeInSeconds}") Long tokenExpireTime) {
    this.tokenExpireTime = tokenExpireTime;
  }

  public ResetPasswordToken generateTokenForUser(User user) {
    ResetPasswordToken token = new ResetPasswordToken();
    token.setUser(user);
    token.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpireTime));
    token.setToken(UUID.randomUUID().toString());
    return token;
  }
}
