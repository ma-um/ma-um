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
import com.spuit.maum.authserver.web.aspect.AuthenticateAspect;
import com.spuit.maum.authserver.web.response.ApiResponse;
import com.spuit.maum.authserver.web.response.user.AuthenticateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
 * UserController 슬라이스 테스트.
 *
 * AuthenticationAspect Test 추가.
 * @author cherrytomato1
 * @version 1.0.1
 */


@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@Import({AopAutoConfiguration.class, AuthenticateAspect.class})
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("이미 존재하는 회원일 경우 200, 없는 회원일 경우 가입 후 201을 응답한다.")
  public void validateUserOrRegisterTest() throws Exception {
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
    registerHeaders.add("Authorization", "token " + INVALID_OAUTH_ID);

    //then
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), findHeaders, findResponseString,
        status().isOk());
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), registerHeaders, registerResponseString,
        status().isCreated());
  }

  @Test
  @DisplayName("Authorization 헤더가 비었거나 prefix가 token이 아닐경우 경우, 400을 반환한다")
  public void authenticationAspectTest() throws Exception {
    //given
    final String INVALID_HEADER = "auth";
    final String INVALID_PREFIX = "tokk";
    final String PREFIX = "token ";
    final String TEST_OAUTH_ID = "1111";

    IllegalArgumentException invalidPrefixException = new IllegalArgumentException(
        "Authorization value must start with [ " + PREFIX + " ]");

    final String uri = "/api/v1/user/load-user";

    //when
    ApiResponse<?> invalidPrefixResponse = ApiResponse.of(HttpStatus.BAD_REQUEST,
        invalidPrefixException.getMessage(), null);
    String invalidPrefixResponseString = objectMapper.writeValueAsString(invalidPrefixResponse);

    MultiValueMap<String, String> invalidHeaders = new LinkedMultiValueMap<>();
    invalidHeaders.add(INVALID_HEADER, "token " + TEST_OAUTH_ID);

    MultiValueMap<String, String> invalidPrefixHeaders = new LinkedMultiValueMap<>();
    invalidPrefixHeaders.add("Authorization", INVALID_PREFIX + TEST_OAUTH_ID);

    //then
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), invalidHeaders, "",
        status().isBadRequest());
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), invalidPrefixHeaders,
        invalidPrefixResponseString,
        status().isBadRequest());

  }


  @Test
  @DisplayName("존재하는 회원일 경우 200과 Id를, 없는 회원일 경우 404를 응답한다.")
  public void authenticateAndGetUserIdTest() throws Exception {
    //given
    final String TEST_OAUTH_ID = "123t";
    final String INVALID_OAUTH_ID = "123f";
    final String TEST_USER_ID = "M123";

    User user = User.builder().oauthId(TEST_OAUTH_ID).id(TEST_USER_ID).build();

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
    mockMvcGetAssert(uri, new LinkedMultiValueMap<>(), unauthorizedHeaders,
        unauthorizedResponseString,
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
