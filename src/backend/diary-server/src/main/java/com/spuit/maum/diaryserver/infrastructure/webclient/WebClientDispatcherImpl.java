package com.spuit.maum.diaryserver.infrastructure.webclient;

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
    try {
      Thread.sleep(2000L);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    return getDummyEmotion(content);
  }

  @Override
  public void setEmotionByDiaryId(String diaryId, Emotion emotion) {
    log.info("diary id - {} , emotion - {}", diaryId, emotion);
  }

  @Override
  public Music findMusicByDiaryId(String diaryId) {
    return getDummyMusic(diaryId);
  }

  @Override
  public Emotion findEmotionByDiaryId(String diaryId) {
    return getDummyEmotion(diaryId);
  }

  @Override
  public List<Music> findAllMusicByDiaryId(String diaryId) {
    List<Music> musicList = new ArrayList<>(3);
    for (int i = 0; i < 3 ; i++) {
      musicList.add(getDummyMusic(diaryId));
    }
    return musicList;
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
}
