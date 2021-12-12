package com.wenox.anonymization.core.config;

import com.wenox.anonymization.core.security.filter.JwtAuthenticationFilter;
import com.wenox.anonymization.core.security.filter.JwtAuthorizationFilter;
import com.wenox.anonymization.core.security.service.JwtService;
import com.wenox.anonymization.core.security.service.JwtUserDetailsService;
import com.wenox.anonymization.core.security.filter.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtUserDetailsService userDetailsService;
  private final JwtService jwtService;

  public SecurityConfiguration(JwtUserDetailsService userDetailsService, JwtService jwtService) {
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter();
  }

  @Bean
  public RequestLoggingFilter requestLoggingFilter() {
    return new RequestLoggingFilter();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/api/v1/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/login")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), jwtService))
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(requestLoggingFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
