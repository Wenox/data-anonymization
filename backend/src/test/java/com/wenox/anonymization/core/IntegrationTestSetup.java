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
public class IntegrationTestSetup {

  @LocalServerPort
  private int serverPort;

  public RequestSpecification adminClient() {

    String accessToken = given()
        .basePath("/api/v1/auth/login")
        .port(serverPort)
        .contentType(MediaType.APPLICATION_JSON.toString())
        .body(new AdminDetails())
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .header("access_token");

    return given()
        .basePath("/api/v1")
        .port(serverPort)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .accept(MediaType.APPLICATION_JSON.toString())
        .contentType(MediaType.APPLICATION_JSON.toString());
  }

  public RequestSpecification verifiedUserClient() {

    String accessToken = given()
        .basePath("/api/v1/auth/login")
        .port(serverPort)
        .contentType(MediaType.APPLICATION_JSON.toString())
        .body(new VerifiedUserDetails())
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .header("access_token");

    return given()
        .basePath("/api/v1")
        .port(serverPort)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .accept(MediaType.APPLICATION_JSON.toString())
        .contentType(MediaType.APPLICATION_JSON.toString());
  }

  static class AdminDetails {
    private String email = "admin@admin.com";
    private String password = "admin";

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  static class VerifiedUserDetails {
    private String email = "user@user.com";
    private String password = "user";

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

}