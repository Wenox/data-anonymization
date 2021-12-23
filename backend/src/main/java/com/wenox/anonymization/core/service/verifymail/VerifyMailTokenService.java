package com.wenox.anonymization.core.service.verifymail;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.VerifyMailToken;
import com.wenox.anonymization.core.repository.VerifyMailTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyMailTokenService {

  @Value("${core.verifyMailToken.expireTimeInSeconds}")
  private Long tokenExpireTime;

  private final VerifyMailTokenRepository repository;

  public VerifyMailTokenService(VerifyMailTokenRepository repository) {
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

  public VerifyMailToken findByToken(String token) {
    return repository.findByToken(token);
  }
}
