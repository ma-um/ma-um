package com.spuit.maum.diaryserver.web.aspect;

import com.spuit.maum.diaryserver.domain.common.exception.UnauthorizedException;
import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcher;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

/**
 * 토큰의 유효성을 검사하는 aspect.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticateAspect {

  private final WebClientDispatcher webClientDispatcher;

  /**
   *
   * @param joinPoint AOP 조인포인트
   * @return 원래 메서드의 반환값 반환
   * @throws Throwable proceed 과정에서 발생하는 throwable.
   */
  @Around("execution(* *(.., @AuthenticationParameter (*), ..))")
  public Object convertUser(ProceedingJoinPoint joinPoint) throws Throwable {

    final String USER_ID_PARAM_NAME = "userId";
    final String AUTH_TOKEN_PARAM_NAME = "token";

    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    List<String> parameters = Arrays.asList(codeSignature.getParameterNames());
    Object[] args = joinPoint.getArgs();

    try {
      String authorization = (String) args[parameters.indexOf(AUTH_TOKEN_PARAM_NAME)];
      log.info("Authenticate Token, authorization - {}", authorization);
      String userId = webClientDispatcher.authenticateAndGetUserId(authorization);
      args[parameters.indexOf(USER_ID_PARAM_NAME)] = userId;
    } catch (UnauthorizedException unauthorizedException) {
      throw unauthorizedException;
    } catch (Exception exception) {
      throw new IllegalArgumentException("invalid authorization header");
    }
    return joinPoint.proceed(args);
  }
}
