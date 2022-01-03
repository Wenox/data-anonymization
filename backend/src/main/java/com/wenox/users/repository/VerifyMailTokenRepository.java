package com.wenox.users.repository;

import com.wenox.users.domain.VerifyMailToken;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyMailTokenRepository extends CrudRepository<VerifyMailToken, UUID> {

  VerifyMailToken findByToken(String token);

  VerifyMailToken findByUserEmail(String email);
}

