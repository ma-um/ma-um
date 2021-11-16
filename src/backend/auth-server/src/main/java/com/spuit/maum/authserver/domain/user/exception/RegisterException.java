package com.spuit.maum.authserver.domain.user.exception;

import lombok.Getter;

/**
 * save()에서 발생하는 예외를 핸들링
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Getter
public class RegisterException extends RuntimeException {

  private final Object exception;

  public RegisterException(Throwable exception) {
    super(String.format("register user exception cause by %s", exception));
    this.exception = exception;
  }
}
