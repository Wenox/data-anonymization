package com.wenox.users.service.verifymail;

import com.wenox.users.domain.User;
import com.wenox.users.domain.VerifyMailToken;
import com.wenox.users.repository.VerifyMailTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyMailTokenService {

  private final Long tokenExpireTime;
  private final VerifyMailTokenRepository repository;

  public VerifyMailTokenService(@Value("${core.verifyMailToken.expireTimeInSeconds}") Long tokenExpireTime,
                                VerifyMailTokenRepository repository) {
    this.tokenExpireTime = tokenExpireTime;
    this.repository = repository;
  }

  public VerifyMailToken generateTokenForUser(User user) {
    if (user.isVerified()) {
      throw new RuntimeException("This user is already verified.");
    }
    VerifyMailToken token = new VerifyMailToken();
    token.setUser(user);
    token.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpireTime));
    token.setToken(UUID.randomUUID().toString());
    return repository.save(token);
  }

  public VerifyMailToken renewTokenExpirationTime(VerifyMailToken token) {
    token.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpireTime));
    repository.save(token);
    return token;
  }

  public VerifyMailToken findByToken(String token) {
    return repository.findByToken(token);
  }

  public VerifyMailToken findByUserEmail(String email) {
    return repository.findByUserEmail(email);
  }
}
