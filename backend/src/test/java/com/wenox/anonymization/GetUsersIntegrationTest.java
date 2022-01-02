package com.wenox.anonymization;

import static org.hamcrest.Matchers.equalTo;


import com.wenox.users.WebPostgreSQLSpecification;
import org.junit.jupiter.api.Test;

//@Sql(scripts = "/scripts/users.sql")
public class GetUsersIntegrationTest extends WebPostgreSQLSpecification {

  @Test
  public void getAllAsAdmin() {
    adminClient()
        .when()
        .get("/users")
        .then()
        .statusCode(200)
        .body("size()", equalTo(3));
  }

  @Test
  public void getAllAsVerifiedUser() {
    verifiedUserClient()
        .when()
        .get("/users")
        .then()
        .statusCode(403);
  }

  @Test
  public void getAllAsUnverifiedUser() {
    unverifiedUserClient()
        .when()
        .get("/users")
        .then()
        .statusCode(403);
  }

  @Test
  public void getAllAsUnauthenticated() {
    unauthenticatedClient()
        .when()
        .get("/users")
        .then()
        .statusCode(403);
  }
}
