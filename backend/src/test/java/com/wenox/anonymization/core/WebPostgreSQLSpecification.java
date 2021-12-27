package com.wenox.anonymization.core;

import static io.restassured.RestAssured.given;


import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebPostgreSQLSpecification extends PostgreSQLSpecification {

  @LocalServerPort
  private int serverPort;

  public RequestSpecification adminClient() {
    return authenticatedClient(new AdminDetails());
  }

  public RequestSpecification verifiedUserClient() {
    return authenticatedClient(new VerifiedUserDetails());
  }

  public RequestSpecification unverifiedUserClient() {
    return authenticatedClient(new UnverifiedUserDetails());
  }

  public RequestSpecification authenticatedClient(AccountDetails details) {
    String accessToken = authenticate(details);
    return unauthenticatedClient()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
  }

  public String authenticate(AccountDetails details) {
    return unauthenticatedClient()
        .port(serverPort)
        .contentType(MediaType.APPLICATION_JSON.toString())
        .body(details)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .header("access_token");
  }

  public RequestSpecification unauthenticatedClient() {
    return given()
        .basePath("/api/v1")
        .port(serverPort)
        .accept(MediaType.APPLICATION_JSON.toString())
        .contentType(MediaType.APPLICATION_JSON.toString());
  }

  static class AccountDetails {
    private final String email;
    private final String password;

    public AccountDetails(String email, String password) {
      this.email = email;
      this.password = password;
    }

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }
  }

  static class AdminDetails extends AccountDetails {
    public AdminDetails() {
      super("admin@admin.com", "admin");
    }
  }

  static class VerifiedUserDetails extends AccountDetails {
    public VerifiedUserDetails() {
      super("user@user.com", "user");
    }
  }

  static class UnverifiedUserDetails extends AccountDetails {
    public UnverifiedUserDetails() {
      super("unverified@unverified.com", "unverified");
    }
  }
}