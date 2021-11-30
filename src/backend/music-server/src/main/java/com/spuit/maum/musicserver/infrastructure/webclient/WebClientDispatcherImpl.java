package com.spuit.maum.musicserver.infrastructure.webclient;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.musicserver.domain.common.exception.UnauthorizedException;
import com.spuit.maum.musicserver.domain.music.MusicDto;
import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationWebClientResponse.MusicId;
import com.spuit.maum.musicserver.web.response.ApiResponse;
import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class WebClientDispatcherImpl implements WebClientDispatcher {

  private final WebClient webClient;

  //  @Value("${url.auth.base}")
  private String authUrl = "http://localhost:8091/api/v1/user";
  //  @Value("${url.emotion.base}")
  private String djangoServerBaseUrl = "http://localhost:8000/";

  private String diaryServer = "http://localhost:8092/api/v1/";
  private String emotionUrl = "emotion/diary2emotion/";
  private String recommendationUrl = "recommendation/music_recommendation/";

  private final Integer MAX_MUSIC_REC_COUNT = 3;

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
  public DiaryEmotionResponse diary2EmotionRequest(String content) {
    String uri = djangoServerBaseUrl + emotionUrl;
    log.info("diary2emotion url - {}", uri);

    DiaryEmotionResponse diaryEmotionResponse;
    try {
      diaryEmotionResponse = webClient
          .post()
          .uri(uri)
          .header("Content-Type", "application/json")
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
  public MusicRecommendationWebClientResponse musicRecommendation(MusicRecommendationRequest musicRecommendationRequest) {
    String uri = djangoServerBaseUrl + recommendationUrl;
    try{
      MusicRecommendationWebClientResponse musicRecommendationWebClientResponse = webClient
          .post()
          .uri(uri)
          .header("Content-Type", "application/json")
          .bodyValue(musicRecommendationRequest)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(MusicRecommendationWebClientResponse.class)
          .block();

      List<MusicId> musicIdList = musicRecommendationWebClientResponse.getMusicIdList();
      while (musicIdList.size() > 3) {
        musicIdList.remove(musicIdList.size() - 1);
      }

      return musicRecommendationWebClientResponse;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }
  @Override
  public List<String> getAllDiaryByUserId(String userId) {
    String uri = diaryServer + "/" + userId;

    try {
      ApiResponse<?> response = webClient
          .get()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);

      return (List<String>) response.getData();
    } catch (WebClientResponseException ex) {

      throw new RuntimeException(ex.getMessage());
    }
  }
}
