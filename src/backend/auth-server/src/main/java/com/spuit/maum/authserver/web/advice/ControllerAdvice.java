package com.spuit.maum.authserver.web.advice;

import com.spuit.maum.authserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.authserver.web.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러 단 예외 발생 시 예외처리 어드바이스
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ApiResponse<?> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
    return ApiResponse.of(HttpStatus.NOT_FOUND, ex.getMessage(), null);
  }
}