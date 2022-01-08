package com.wenox.processing.repository;

import com.wenox.processing.domain.Outcome;
import com.wenox.users.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface OutcomeRepository extends CrudRepository<Outcome, String> {

  List<Outcome> findAllByWorksheetUser(User user);
}
