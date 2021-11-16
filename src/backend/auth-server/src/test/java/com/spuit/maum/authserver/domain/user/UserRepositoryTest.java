package com.spuit.maum.authserver.domain.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * UserRepository의 테스트
 *
 * DataRepositoryTest로 변경
 * @author cherrytomato1
 * @version 1.0.1
 */
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  private final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);

  @Test
  @DisplayName("User 엔티티를 UserRepository로 저장한다")
  public void saveUserTest() {
    //given
    final String TEST_OAUTH_ID = "11111111t1";
    User user = User.builder().oauthId(TEST_OAUTH_ID).build();

    LOGGER.info("test user - {}", user);

    //when
    User resultUser = userRepository.save(user);

    LOGGER.info("result user entity - {}", resultUser);

    //then
    assertEquals(resultUser, user);
  }

  @Test
  @DisplayName("OauthId로 User를 불러온다.")
  public void loadUserByOauthId() {
    //given
    final String TEST_OAUTH_ID = "111111111t2";
    String invalidOauthId = "1231";
    User savedUser = userRepository.save(User.builder().oauthId(TEST_OAUTH_ID).build());
    //when
    Optional<User> successUserOptional = userRepository.findByOauthId(TEST_OAUTH_ID);
    Optional<User> failureUserOptional = userRepository.findByOauthId(invalidOauthId);

    //then
    assertTrue(successUserOptional.isPresent());
    assertEquals(successUserOptional.get(), savedUser);

    assertFalse(failureUserOptional.isPresent());
  }
}