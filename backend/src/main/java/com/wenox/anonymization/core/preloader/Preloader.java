package com.wenox.anonymization.core.preloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@ConditionalOnProperty(value = "spring.jpa.hibernate.ddl-auto", havingValue = "create")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Preloader {}
