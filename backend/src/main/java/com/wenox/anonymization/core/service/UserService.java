package com.wenox.anonymization.core.service;

import com.wenox.anonymization.core.domain.User;
import com.wenox.anonymization.core.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }
}
