package com.wenox.infrastructure.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestLoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } finally {
      if (!isAsyncStarted(request)) {
        log(request, response);
      }
    }
  }

  protected void log(HttpServletRequest request, HttpServletResponse response) {
    logger.info(String.format("%s %s: %s", response.getStatus(), request.getMethod(), request.getRequestURI()));
  }
}
