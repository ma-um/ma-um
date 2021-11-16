package com.spuit.maum.authserver.domain.user.exception;


import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
  private final String token;
  public UnauthorizedException(String token) {
    super(String.format("Unauthorized token - {}", token));
    this.token = token;
  }
}
