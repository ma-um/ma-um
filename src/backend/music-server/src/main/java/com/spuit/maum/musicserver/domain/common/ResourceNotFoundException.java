package com.spuit.maum.musicserver.domain.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 자원을 조회할 때 오류가 있으면 발생하는 예외.
 * <p>
 * 도메인 내에서 공통으로 사용, 각 상세 예외는 상속하여 사용
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Getter
public class ResourceNotFoundException extends RuntimeException {

  public static final HttpStatus responseStatus = HttpStatus.NOT_FOUND;
  private final String resourceName;
  private final Object resourceType;
  private final String resourceValue;


  /**
   * @param resourceName 예외 발생한 데이터의 명
   * @param resourceType 예외 발생한 데이터의 타입(.class)
   * @param resourceValue 예외 발생 시 찾으려고 했던 값
   */
  public ResourceNotFoundException(String resourceName, Object resourceType, String resourceValue) {
    super(String.format("%s not found with %s : %s", resourceName, resourceType, resourceValue));
    this.resourceType = resourceType;
    this.resourceName = resourceName;
    this.resourceValue = resourceName;
  }
}

