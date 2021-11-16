package com.spuit.maum.authserver.web.aspect;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 토큰의 유효성을 검사하는 aspect.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Aspect
@Component
@Slf4j
public class AuthenticateAspect {

  /**
   *
   * @param joinPoint AOP 조인포인트
   * @return 원래 메서드의 반환값 반환
   * @throws Throwable proceed 과정에서 발생하는 throwable.
   */
  @Around("execution(* *(.., @AuthenticationParameter (*), ..))")
  public Object convertUser(ProceedingJoinPoint joinPoint) throws Throwable {

    final String TOKEN_PREFIX = "token ";

    //header 가져오기
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    String authorization = request.getHeader("Authorization");

    log.info("Authenticate Token, authorization - {}", authorization);

    //토큰 validation
    if (authorization.isEmpty()) {
      throw new IllegalArgumentException("Authorization never be empty!");
    }
    if (!authorization.startsWith(TOKEN_PREFIX)) {
      throw new IllegalArgumentException("Authorization value must start with [ " + TOKEN_PREFIX + " ]");
    }

    String token = authorization.replaceAll(TOKEN_PREFIX, "");

    //파라미터 중 String, TOKEN_PREFIX로 시작하는 값 변경
    Object[] args = Arrays
        .stream(joinPoint.getArgs()).map(data -> {
          if (data instanceof String && ((String) data).startsWith(TOKEN_PREFIX)) {
            data = token;
          }
          return data;
        }).toArray();

    log.info("Validated token - {}", token);
    //proceed method
    return joinPoint.proceed(args);
  }
}
