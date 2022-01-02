package com.wenox.users.repository;

import com.wenox.users.domain.UndoRemoveAccountToken;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UndoRemoveAccountTokenRepository extends CrudRepository<UndoRemoveAccountToken, UUID> {

  UndoRemoveAccountToken findByToken(String token);
}