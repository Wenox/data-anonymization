package com.wenox.users.service.removeaccount;

import com.wenox.users.domain.UndoRemoveAccountToken;
import com.wenox.users.domain.User;
import com.wenox.users.repository.UndoRemoveAccountTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UndoRemoveAccountTokenService {

  @Value("${core.undoRemoveAccountToken.expireTimeInSeconds}")
  private Long tokenExpireTime;

  private final UndoRemoveAccountTokenRepository repository;

  public UndoRemoveAccountTokenService(UndoRemoveAccountTokenRepository repository) {
    this.repository = repository;
  }

  public UndoRemoveAccountToken generateTokenForUser(User user) {
    UndoRemoveAccountToken token = new UndoRemoveAccountToken();
    token.setUser(user);
    token.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpireTime));
    token.setToken(UUID.randomUUID().toString());
    return repository.save(token);
  }

  public UndoRemoveAccountToken findByToken(String token) {
    return repository.findByToken(token);
  }
}
