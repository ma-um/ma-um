package com.spuit.maum.authserver.application.user;

import com.spuit.maum.authserver.domain.user.User;
import org.springframework.stereotype.Service;

/**
 * User 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

  @Override
  public User findUserByOauthId(String oauthId) {
    return null;
  }

  @Override
  public User registerUserByOauthId(String oauthId) {
    return null;
  }
}
