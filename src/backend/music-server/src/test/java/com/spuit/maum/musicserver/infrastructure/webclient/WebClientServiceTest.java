package com.spuit.maum.musicserver.infrastructure.webclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class WebClientServiceTest {

  //  @Mock(answer = Answers.RETURNS_DEEP_STUBS)

  @Mock
  private WebClient webClient;

  @InjectMocks
  private WebClientService webClientService;

  @Value("${server-url.auth}")
  String TEST_AUTH_URI;

  @Test
  @DisplayName("인증서버 요청을 테스트한다.")
  void authenticationRequestTest() {
    //given

    final String TEST_USER_ID = "testid";
    final String TEST_TOKEN = "testtoken";
    given(webClient
        .get()
        .uri(TEST_AUTH_URI)
        .header("Authorization", TEST_TOKEN)
        .retrieve()
        .bodyToMono(String.class)
        .block()
    ).willReturn(TEST_USER_ID);

    //when
    String resultUserId = webClientService.authenticateAndLoadUserId(TEST_TOKEN);

    //then

    assertEquals(TEST_USER_ID, resultUserId);
  }
}