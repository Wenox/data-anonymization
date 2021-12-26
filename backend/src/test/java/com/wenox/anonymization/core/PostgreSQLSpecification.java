package com.wenox.anonymization.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
public class PostgreSQLSpecification {

  @Container
  public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres")
      .withUsername("postgres")
      .withPassword("postgres")
      .withDatabaseName("anonymisation_db");
}
