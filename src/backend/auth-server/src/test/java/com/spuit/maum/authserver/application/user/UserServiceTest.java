package com.spuit.maum.authserver.application.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.spuit.maum.authserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.authserver.domain.user.User;
import com.spuit.maum.authserver.domain.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class UserServiceTest {

  @MockBean
  UserRepository userRepository;

  private UserService userService;

  private final String TEST_OAUTH_ID = "111111111";
  private final String INVALID_OAUTH_ID = "123";

  private final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

  @BeforeEach
  public void setUp() {
    this.userService = new UserServiceImpl();
  }


  @Test
  @DisplayName("OAuthID로 사용자를 불러온다")
  public void loadUserServiceTest() {
    //given
    User user = User.builder().oauthId(TEST_OAUTH_ID).build();

    given(this.userRepository.findByOauthId(TEST_OAUTH_ID))
        .willReturn(Optional.of(user));
    given(this.userRepository.findByOauthId(INVALID_OAUTH_ID))
        .willReturn(Optional.empty());

    //when
    User successResultUser = userService.findUserByOauthId(TEST_OAUTH_ID);

    //then
    assertEquals(user, successResultUser);
    assertThrows(ResourceNotFoundException.class,
        () -> userService.findUserByOauthId(INVALID_OAUTH_ID));

  }

  @Test
  @DisplayName("OAuthId로 새로운 사용자를 등록한다")
  public void registerUserServiceTest() {
    //given
    User user = User.builder().oauthId(TEST_OAUTH_ID).build();

    given(this.userRepository.save(user)).willReturn(user);

    //when
    User resultUser = userService.registerUserByOauthId(TEST_OAUTH_ID);

    //then
    assertEquals(user, resultUser);
  }
}

}