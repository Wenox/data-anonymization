package com.wenox.anonymization;

import static org.hamcrest.Matchers.equalTo;


import com.wenox.anonymization.core.WebPostgreSQLSpecification;
import java.util.Objects;
import org.junit.jupiter.api.Test;

//@Sql(scripts = "/scripts/users.sql")
public class GetUsersIntegrationTest extends WebPostgreSQLSpecification {

  @Test
  public void getAll() {
    adminClient()
        .when()
        .get("/users")
        .then()
        .statusCode(200)
        .body("size()", equalTo(2));
  }
}
