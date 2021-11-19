package com.spuit.maum.authserver.application.user;

import com.spuit.maum.authserver.domain.user.User;
import com.spuit.maum.authserver.domain.user.UserRepository;
import com.spuit.maum.authserver.domain.user.exception.RegisterException;
import com.spuit.maum.authserver.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public User findUserByOauthId(String oauthId) {
    return userRepository.findByOauthId(oauthId).orElseThrow(() -> new UserNotFoundException(
        "OAuthId", oauthId));
  }

  @Override
  @Transactional
  public User registerUserByOauthId(String oauthId) {
    try {
      return userRepository.save(User.builder().oauthId(oauthId).build());
    } catch (RuntimeException ex) {
      throw new RegisterException(ex);
    }
  }
}
