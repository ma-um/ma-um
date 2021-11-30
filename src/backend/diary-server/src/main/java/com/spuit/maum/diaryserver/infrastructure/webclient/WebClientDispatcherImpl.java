package com.spuit.maum.diaryserver.infrastructure.webclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.domain.common.exception.UnauthorizedException;
import com.spuit.maum.diaryserver.domain.diary.Emotion;
import com.spuit.maum.diaryserver.domain.diary.Music;
import com.spuit.maum.diaryserver.web.response.ApiResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

  private final ObjectMapper mapper = new ObjectMapper();

  //  @Value("${url.auth.base}")
  private String authUrl = "http://localhost:8091/api/v1/user";

  private String musicUrl = "http://localhost:8093/api/v1/music";
  private String emotionUrl = "http://localhost:8093/api/v1/emotion";

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
    String uri = emotionUrl + "/analysis";
    log.info("analysis uri - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .post()
          .uri(uri)
          .bodyValue(content)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);
      Map<String, Object> result = (Map<String, Object>)response.getData();
      return mapper.convertValue(result.get("emotion"), Emotion.class);
    } catch (WebClientResponseException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public void setEmotionByDiaryId(String diaryId, Emotion emotion) {
    String uri = emotionUrl + "/" + diaryId;
    log.info("analysis uri - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .post()
          .uri(uri)
          .bodyValue(new SetCustomEmotionRequest(emotion))
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);

      setRecommendationMusic(diaryId);

    } catch (WebClientResponseException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public Music findMusicByDiaryId(String diaryId) {

    return findAllMusicByDiaryId(diaryId).get(0);

//    return getDummyMusic(diaryId);`
  }

  @Override
  public Emotion findEmotionByDiaryId(String diaryId) {
    String uri = emotionUrl + "/" + diaryId;
    log.info("find emotion uri - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .get()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);
      Map<String, Object> result = (Map<String, Object>)response.getData();
      return mapper.convertValue(result.get("emotion"), Emotion.class).resetTopEmotionValue();
    } catch (WebClientResponseException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public List<Music> findAllMusicByDiaryId(String diaryId) {
    String uri = musicUrl + "/" + diaryId;
    log.info("find emotion uri - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .get()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);
      Object data =((Map<String, Object>)response.getData()).get("musicList");
      List<Music> result = mapper.convertValue(data,
          new TypeReference<List<Music>>(){});
      return result;
    } catch (WebClientResponseException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private Music getDummyMusic(String diaryId) {
    return Music.builder().name("아이와 나의 바다").singer("아이유").jacketUrl(
        "https://cdnimg.melon.co.kr/cm/album/images/000/43/841/43841_500"
            + ".jpg/melon/resize/282/quality/80/optimize").build();
  }

  private Emotion getDummyEmotion(String Content) {

    return Emotion.builder().fear(3).anger(5).disgust(30).bruise(20).embarrassment(22).happiness(55)
        .neutrality(33).pleasure(50).sadness(32).surprise(12).unrest(55).build()
        .setDefaultTopEmotion().resetTopEmotionValue();
  }

  private void setRecommendationMusic(String diaryId){
    String uri = musicUrl + "/recommendation/" + diaryId;
    log.info("recommetation uri - {}", uri);

    try {
      ApiResponse<?> response = webClient
          .get()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ApiResponse.class)
          .block();

      log.info("response - {}", response);
      Object data =((Map<String, Object>)response.getData()).get("musicList");
      List<Music> result = mapper.convertValue(data,
          new TypeReference<List<Music>>(){});
//      return result;
    } catch (WebClientResponseException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
