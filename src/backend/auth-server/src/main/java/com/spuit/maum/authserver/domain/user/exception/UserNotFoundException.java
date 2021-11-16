package com.spuit.maum.authserver.domain.user.exception;

import com.spuit.maum.authserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.authserver.domain.user.User;
import lombok.Getter;

/**
 * User를 찾지 못했을 때 발생하는 예외. ResourceNotFoundException의 하위 예외
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Getter
public class UserNotFoundException extends ResourceNotFoundException {
  public UserNotFoundException(String key, String value) {
    super(key, User.class, value);
  }
}
