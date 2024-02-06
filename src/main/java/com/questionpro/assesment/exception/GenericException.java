package com.questionpro.assesment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

  @Getter
  private final HttpStatus status;
  public GenericException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

}
