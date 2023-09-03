package com.vmichael.springbootapp.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class ExceptionControllerAdvice {
  private final Logger logger = Logger.getLogger(ExceptionControllerAdvice.class.getName());

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> ArithmeticExceptionHandler(final RuntimeException e) {
    logger.log(Level.SEVERE, e.getMessage(), e);
    return ResponseEntity
     .badRequest()
     .body(e.getMessage());
  }
}
