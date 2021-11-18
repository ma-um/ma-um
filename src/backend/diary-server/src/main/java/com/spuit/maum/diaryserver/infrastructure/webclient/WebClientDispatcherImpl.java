package com.spuit.maum.diaryserver.infrastructure.webclient;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.domain.common.exception.UnauthorizedException;
import com.spuit.maum.diaryserver.domain.emotion.Emotion;
import com.spuit.maum.diaryserver.web.response.ApiResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class WebClientDispatcherImpl implements WebClientDispatcher {

  private final WebClient webClient;

  //  @Value("${url.auth.base}")
  private String authUrl = "http://localhost:8091/api/v1/user";

  public WebClientDispatcherImpl(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  @Override
  public String authenticateAndGetUserId(String token) {
    String uri = authUrl;
    log.info("authenticate url - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .get()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, token)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);
      Map<String, String> map = (LinkedHashMap<String, String>) response.getData();
      return map.get("userId");
    } catch (WebClientResponseException ex) {
      throw new UnauthorizedException(token);
    }
  }

  @Override
  public Emotion getEmotionByDiaryContent(String content) {
    return getDummyEmotion(content);
  }

  @Override
  public void setEmotionByDiaryId(String diaryId, Emotion emotion) {
    log.info("diary id - {} , emotion - {}", diaryId, emotion);
  }

  private Emotion getDummyEmotion(String Content) {
    try {
      Thread.sleep(2000L);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    return Emotion.builder().fear(3).anger(5).disgust(30).bruise(20).embarrassment(22).happiness(55)
        .neutrality(33).pleasure(50).sadness(32).surprise(12).unrest(55).build();
  }
}
