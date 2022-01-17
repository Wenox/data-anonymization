package com.wenox.infrastructure.config;

import com.wenox.users.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .ignoredParameterTypes(User.class)
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
      .useDefaultResponseMessages(false)
      .apiInfo(metadata());
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
      .title("Anonymisation Platform")
      .description("REST API for Anonymisation Server")
      .version("1.0.0")
      .license("MIT License")
      .build();
  }
}