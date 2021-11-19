package com.spuit.maum.authserver.web.advice;

import com.spuit.maum.authserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.authserver.domain.user.exception.UnauthorizedException;
import com.spuit.maum.authserver.web.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.service.ApiInfo;

/**
 * 컨트롤러 단 예외 발생 시 예외처리 어드바이스.
 * <p>
 * UnauthorizedException 핸들링 추가.
 *
 * @author cherrytomato1
 * @version 1.0.1
 */

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = UnauthorizedException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ApiResponse<?> UnauthorizedExceptionHandler(UnauthorizedException ex) {
    return ApiResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getToken());
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse<?> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
    return ApiResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
  }
}