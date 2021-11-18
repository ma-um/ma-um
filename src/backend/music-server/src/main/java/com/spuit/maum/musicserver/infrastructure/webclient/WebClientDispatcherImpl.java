package com.spuit.maum.musicserver.infrastructure.webclient;

import com.spuit.maum.musicserver.web.request.MusicRecommendationRequest;
import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import com.spuit.maum.musicserver.web.response.Music.MusicRecommendationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class WebClientDispatcherImpl implements WebClientDispatcher {

  private final WebClient webClient;

  //  @Value("${url.auth.base}")
  private String authUrl = "http://localhost:8091/api/v1/user";
  //  @Value("${url.emotion.base}")
  private String djangoServerBaseUrl = "http://localhost:8092/api/v1/";
  private String emotionUrl = "emotion/diary2emotion";
  private String recommendationUrl = "recommendation/music_recommendation";

  public WebClientDispatcherImpl(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  @Override
  public DiaryEmotionResponse diary2EmotionRequest(String content) {
    String uri = djangoServerBaseUrl + emotionUrl;
    log.info("diary2emotion url - {}", uri);

    DiaryEmotionResponse diaryEmotionResponse;
    try {
      diaryEmotionResponse = webClient
          .post()
          .uri(uri)
          .bodyValue(content)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(DiaryEmotionResponse.class)
          .block();

      log.info("response - {}", diaryEmotionResponse);
      return diaryEmotionResponse;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public MusicRecommendationResponse musicRecommendation(MusicRecommendationRequest musicRecommendationRequest) {
    String uri = djangoServerBaseUrl + recommendationUrl;
    try{
      return webClient
          .post()
          .uri(uri)
          .bodyValue(musicRecommendationRequest)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(MusicRecommendationResponse.class)
          .block();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }
}
