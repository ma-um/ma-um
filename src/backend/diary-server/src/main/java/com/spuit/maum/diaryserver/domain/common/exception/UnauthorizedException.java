package com.spuit.maum.diaryserver.domain.common.exception;


import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
  private final String token;
  public UnauthorizedException(String token) {
    super(String.format("Unauthorized token %s", token));
    this.token = token;
  }
}
