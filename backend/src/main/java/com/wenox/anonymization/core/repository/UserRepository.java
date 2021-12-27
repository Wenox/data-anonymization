package com.wenox.anonymization.core.repository;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.domain.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  List<User> findAll();

  List<User> findAllByVerifiedFalseAndForceRemovalFalseAndStatusNot(UserStatus status);

  List<User> findAllByForceRemovalFalseAndStatusNot(UserStatus status);

  List<User> findAllByForceRemovalFalseAndMarkedForRemovalFalseAndStatus(UserStatus status);

  List<User> findAllByForceRemovalTrueOrMarkedForRemovalTrueAndStatusNot(UserStatus status);
}
