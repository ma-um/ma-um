package com.spuit.maum.diaryserver.webclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientService를 테스트한다.
 *
 *  header를 추가하면 NPE 가 발생하는 문제 해결 필요
 */

@ExtendWith(MockitoExtension.class)
class WebClientDispatcherTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  WebClient webClient;

  @Mock(answer = Answers.RETURNS_SELF)
  WebClient.Builder webClientBuilder;

  WebClientDispatcher webClientDispatcher;

//  @Value("${url.auth.base}")
  String TEST_AUTH_URI = "http://localhost:8091/api/v1/user";
  private final Logger LOGGER = LoggerFactory.getLogger(WebClientDispatcher.class);

  @BeforeEach
  void setUp() {
    LOGGER.info("auth server - {}", TEST_AUTH_URI);
    given(webClientBuilder.build()).willReturn(webClient);

    LOGGER.info("webclient equals-{}", webClientBuilder.build().equals(webClient));

    this.webClientDispatcher = new WebClientDispatcherImpl(webClientBuilder);
  }

  @Test
  @DisplayName("인증서버 요청을 테스트한다.")
  void authenticationRequestTest() {
    //given

    final String TEST_URI = TEST_AUTH_URI + "/load-user";
    final String TEST_USER_ID = "testid";
    final String TEST_TOKEN = "testtoken";


    given(webClient
        .get()
        .uri(TEST_URI)
        .accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, TEST_TOKEN)
        .retrieve()
        .bodyToMono(String.class)
        .block()
    ).willReturn(TEST_USER_ID);

    //when
    String resultUserId = webClientDispatcher.authenticateAndGetUserId(TEST_TOKEN);

    //then
    assertEquals(TEST_USER_ID, resultUserId);
  }
}