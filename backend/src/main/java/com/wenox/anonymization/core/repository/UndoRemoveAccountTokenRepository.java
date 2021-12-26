package com.wenox.anonymization.core.repository;

import com.wenox.anonymization.core.domain.UndoRemoveAccountToken;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UndoRemoveAccountTokenRepository extends CrudRepository<UndoRemoveAccountToken, UUID> {

  UndoRemoveAccountToken findByToken(String token);
}