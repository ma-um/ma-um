package com.spuit.maum.authserver.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spuit.maum.authserver.application.user.UserService;
import com.spuit.maum.authserver.domain.user.User;
import com.spuit.maum.authserver.domain.user.exception.UnauthorizedException;
import com.spuit.maum.authserver.domain.user.exception.UserNotFoundException;
import com.spuit.maum.authserver.web.response.ApiResponse;
import com.spuit.maum.authserver.web.response.user.AuthenticateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * UserController 슬라이스 테스트
 *
 * @author cherrytomato1
 * @version 1.0.0
 */


@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("이미 존재하는 회원일 경우 200, 없는 회원일 경우 가입 후 201을 응답한다.")
  void validateUserOrRegisterTest() throws Exception {
    //given
    final String TEST_OAUTH_ID = "123t";
    final String INVALID_OAUTH_ID = "123f";
    User existingUser = User.builder().oauthId(TEST_OAUTH_ID).build();
    User newUser = User.builder().oauthId(INVALID_OAUTH_ID).build();

    given(this.userService.findUserByOauthId(TEST_OAUTH_ID)).willReturn(existingUser);
    given(this.userService.findUserByOauthId(INVALID_OAUTH_ID))
        .willThrow(new UserNotFoundException("OAuth Id", INVALID_OAUTH_ID));
    given(this.userService.registerUserByOauthId(INVALID_OAUTH_ID)).willReturn(newUser);
    String uri = "/api/v1/user/load-user";

    //when
    ApiResponse<?> successFindResponse = ApiResponse.defaultOk(TEST_OAUTH_ID);
    ApiResponse<?> successRegisterResponse = ApiResponse.of(HttpStatus.CREATED, "success",
        INVALID_OAUTH_ID);
    String findResponseString = objectMapper.writeValueAsString(successFindResponse);
    String registerResponseString = objectMapper.writeValueAsString(successRegisterResponse);

    MultiValueMap<String, String> findHeaders = new LinkedMultiValueMap<>();
    findHeaders.add("Authorization", "token " + TEST_OAUTH_ID);

    MultiValueMap<String, String> registerHeaders = new LinkedMultiValueMap<>();
    findHeaders.add("Authorization", "token " + INVALID_OAUTH_ID);

    //then
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), findHeaders, findResponseString,
        status().isOk());
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), registerHeaders, registerResponseString,
        status().isCreated());
  }

  @Test
  @DisplayName("존재하는 회원일 경우 200과 Id를, 없는 회원일 경우 404를 응답한다.")
  void authenticateAndGetUserIdTest() throws Exception{
    //given
    final String TEST_OAUTH_ID = "123t";
    final String INVALID_OAUTH_ID = "123f";
    final String TEST_USER_ID = "M123";

    User user = User.builder().oauthId(TEST_OAUTH_ID).build();

    given(this.userService.findUserByOauthId(TEST_OAUTH_ID)).willReturn(user);
    given(this.userService.findUserByOauthId(INVALID_OAUTH_ID))
        .willThrow(new UserNotFoundException("OAuth Id", INVALID_OAUTH_ID));

    String uri = "/api/v1/user/";

    //when
    ApiResponse<?> successResponse = ApiResponse.of(HttpStatus.OK, "success",
        new AuthenticateResponse(TEST_USER_ID));
    String successResponseString = objectMapper.writeValueAsString(successResponse);

    UnauthorizedException unauthorizedException = new UnauthorizedException(INVALID_OAUTH_ID);
    ApiResponse<?> unauthorizedResponse = ApiResponse.of(HttpStatus.UNAUTHORIZED,
        unauthorizedException.getMessage(), INVALID_OAUTH_ID);

    String unauthorizedResponseString = objectMapper.writeValueAsString(unauthorizedResponse);

    MultiValueMap<String, String> successHeaders = new LinkedMultiValueMap<>();
    successHeaders.add("Authorization", "token " + TEST_OAUTH_ID);

    MultiValueMap<String, String> unauthorizedHeaders = new LinkedMultiValueMap<>();
    unauthorizedHeaders.add("Authorization", "token " + INVALID_OAUTH_ID);

    //then

    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), successHeaders, successResponseString,
        status().isOk());
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), unauthorizedHeaders, unauthorizedResponseString,
        status().isUnauthorized());
  }

  private void mockMvcPostAssert(String uri, String content, String exceptedResultString,
      MultiValueMap<String, String> headers, ResultMatcher exceptedResultMat) throws Exception {

    mockMvc.perform(post(uri)
        .content(content)
        .headers(new HttpHeaders(headers))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(exceptedResultMat)
        .andExpect(content().string(exceptedResultString))
        .andDo(print());
  }

  private void mockMvcGetAssert(String uri, MultiValueMap<String, String> params,
      MultiValueMap<String, String> headers,
      String exceptedResultString, ResultMatcher exceptedResultMat) throws Exception {

    mockMvc.perform(get(uri)
        .params(params)
        .headers(new HttpHeaders(headers)))
        .andExpect(exceptedResultMat)
        .andExpect(content().string(exceptedResultString))
        .andDo(print());
  }
}

/*

package com.ggg.logg.web.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.ggg.logg.TestConstant.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggg.logg.domain.common.DuplicatedException;
import com.ggg.logg.domain.user.exception.UserNotFoundException;
import com.ggg.logg.web.request.user.UserRegisterRequest;
import com.ggg.logg.web.response.ApiResponse;
import com.ggg.logg.domain.common.ResourceNotFoundException;
import com.ggg.logg.domain.user.exception.IllegalPasswordException;
import com.ggg.logg.web.request.user.UserLoginRequest;
import com.ggg.logg.web.response.user.UserLoginResponse;
import com.ggg.logg.application.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@MockMvcFilterConfig
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @BeforeEach
  public void setUp() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("존재하는 사용자 Email와 비밀번호를 입력하면 200의 응답을 받는다")
  public void loginSuccessTest() throws Exception {
    //given
    given(this.userService.loginByEmailAndPassword(TEST_EMAIL, TEST_PASSWORD))
        .willReturn(TEST_USER_DTO);
    String uri = "/api/v1/user/login";

    //when
    UserLoginRequest userLoginRequest = new UserLoginRequest(TEST_EMAIL, TEST_PASSWORD);
    String content = objectMapper.writeValueAsString(userLoginRequest);

    ApiResponse<UserLoginResponse> response = ApiResponse.of(HttpStatus.OK, "success",
        UserLoginResponse.ofUserDto(TEST_USER_DTO));
    String exceptedResultString = objectMapper.writeValueAsString(response);

    //then
    mockMvcPostAssert(uri, content, exceptedResultString, status().isOk());
  }

  @Test
  @DisplayName("존재하지 않는 사용자 ID를 입력하면 404 응답을 보낸다.")
  public void invalidIdLoginFailureTest() throws Exception {
    //given
    ResourceNotFoundException exceptedException = new UserNotFoundException("email", INVALID_EMAIL);
    given(this.userService.loginByEmailAndPassword(INVALID_EMAIL, TEST_PASSWORD))
        .willThrow(exceptedException);
    String uri = "/api/v1/user/login";

    //when
    UserLoginRequest userLoginRequest = new UserLoginRequest(INVALID_EMAIL, TEST_PASSWORD);
    String content = objectMapper.writeValueAsString(userLoginRequest);

    ApiResponse<?> response = ApiResponse.of(HttpStatus.NOT_FOUND, exceptedException.getMessage(),
        null);
    String exceptedResultString = objectMapper.writeValueAsString(response);

    //then
    mockMvcPostAssert(uri, content, exceptedResultString, status().isNotFound());
  }

  @Test
  @DisplayName("올바르지 않은 비밀번호를 입력하면 404 응답을 보낸다.")
  public void invalidPasswordLoginFailureTest() throws Exception {
    //given
    RuntimeException exceptedException = new IllegalPasswordException(TEST_EMAIL, INVALID_PASSWORD);
    given(this.userService.loginByEmailAndPassword(TEST_EMAIL, INVALID_PASSWORD))
        .willThrow(exceptedException);
    String uri = "/api/v1/user/login";

    //when
    UserLoginRequest userLoginRequest = new UserLoginRequest(TEST_EMAIL, INVALID_PASSWORD);
    String content = objectMapper.writeValueAsString(userLoginRequest);

    ApiResponse<?> response = ApiResponse
        .of(HttpStatus.UNAUTHORIZED, exceptedException.getMessage(),
            null);
    String exceptedResultString = objectMapper.writeValueAsString(response);

    //then
    mockMvcPostAssert(uri, content, exceptedResultString, status().isUnauthorized());
  }

  @Test
  @DisplayName("이메일 중복체크 컨트롤러를 테스트한다.")
  public void checkDuplicateUserEmailControllerTest() throws Exception {
    //given
    given(this.userService.isDuplicateEmail(TEST_EMAIL)).willReturn(true);
    given(this.userService.isDuplicateEmail(INVALID_EMAIL)).willReturn(false);
    String uri = "/api/v1/user/check-email";
    DuplicatedException exceptedException = new DuplicatedException("email", TEST_EMAIL);

    //when
    ApiResponse<?> duplicateResponse = ApiResponse
        .of(HttpStatus.CONFLICT, exceptedException.getMessage(),
            exceptedException.getValue());
    ApiResponse<?> successResponse = ApiResponse.of(HttpStatus.OK, "success", INVALID_EMAIL);
    String duplicateExceptedResultString = objectMapper.writeValueAsString(duplicateResponse);
    String successExceptedResultString = objectMapper.writeValueAsString(successResponse);

    MultiValueMap<String, String> duplicateParams = new LinkedMultiValueMap<>();
    duplicateParams.add("email", TEST_EMAIL);

    MultiValueMap<String, String> successParams = new LinkedMultiValueMap<>();
    successParams.add("email", INVALID_EMAIL);

    //then
    mockMvcGetAssert(uri, duplicateParams, duplicateExceptedResultString, status().isConflict());
    mockMvcGetAssert(uri, successParams, successExceptedResultString, status().isOk());
  }

  @Test
  @DisplayName("닉네임 중복체크 컨트롤러를 테스트한다.")
  public void checkDuplicateUserNicknameControllerTest() throws Exception {
    //given
    given(this.userService.isDuplicateNickname(TEST_NICKNAME)).willReturn(true);
    given(this.userService.isDuplicateNickname(INVALID_NICKNAME)).willReturn(false);
    String uri = "/api/v1/user/check-nickname";
    DuplicatedException exceptedException = new DuplicatedException("nickname", TEST_NICKNAME);

    //when
    ApiResponse<?> duplicateResponse = ApiResponse
        .of(HttpStatus.CONFLICT, exceptedException.getMessage(),
            exceptedException.getValue());

    ApiResponse<?> successResponse = ApiResponse.of(HttpStatus.OK, "success", INVALID_NICKNAME);
    String duplicateExceptedResultString = objectMapper.writeValueAsString(duplicateResponse);
    String successExceptedResultString = objectMapper.writeValueAsString(successResponse);

    MultiValueMap<String, String> duplicateParams = new LinkedMultiValueMap<>();
    duplicateParams.add("nickname", TEST_NICKNAME);

    MultiValueMap<String, String> successParams = new LinkedMultiValueMap<>();
    successParams.add("nickname", INVALID_NICKNAME);

    //then
    mockMvcGetAssert(uri, duplicateParams, duplicateExceptedResultString, status().isConflict());
    mockMvcGetAssert(uri, successParams, successExceptedResultString, status().isOk());
  }

  @Test
  @DisplayName("회원가입 컨트롤러를 테스트한다")
  public void registerUserControllerTest() throws Exception {
    given(this.userService.registerUser(TEST_USER_DTO)).willReturn(TEST_USER_DTO);
    String uri = "/api/v1/user/register";

    UserRegisterRequest userRegisterRequest =
        UserRegisterRequest.builder().email(TEST_EMAIL).nickname(TEST_NICKNAME)
            .password(TEST_PASSWORD).build();

    //when
    ApiResponse<?> successResponse = ApiResponse.of(HttpStatus.CREATED, "success", TEST_EMAIL);
    String successExceptedResultString = objectMapper.writeValueAsString(successResponse);
    String content = objectMapper.writeValueAsString(userRegisterRequest);

    //then
    mockMvcPostAssert(uri, content, successExceptedResultString, status().isCreated());

  }

  private void mockMvcPostAssert(String uri, String content, String exceptedResultString,
      ResultMatcher exceptedResultMat) throws Exception {

    mockMvc.perform(post(uri)
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(exceptedResultMat)
        .andExpect(content().string(exceptedResultString))
        .andDo(print());
  }

  private void mockMvcGetAssert(String uri, MultiValueMap<String, String> params,
      String exceptedResultString, ResultMatcher exceptedResultMat) throws Exception {

    mockMvc.perform(get(uri)
        .params(params))
        .andExpect(exceptedResultMat)
        .andExpect(content().string(exceptedResultString))
        .andDo(print());
  }

}
 */