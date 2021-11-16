package com.spuit.maum.authserver.application.user;

import com.spuit.maum.authserver.application.ApplicationService;
import com.spuit.maum.authserver.domain.user.User;

/**
 * User 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface UserService extends ApplicationService {
  User findUserByOauthId(String oauthId);

  User registerUserByOauthId(String oauthId);
}
