package com.spuit.maum.musicserver.web.controller;

import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationRequest;
import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationWebClientResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TestController {

  private final WebClientDispatcher webClientDispatcher;

  @PostMapping("api/v1/emotion/diary2emotion")
  public ResponseEntity<?> test(@RequestBody String content) {
    String result = "[54. 28. 36. 88. 31. 35. 34. 20. 32. 30. 36.]";
    DiaryEmotionResponse diaryEmotionResponse =
        DiaryEmotionResponse.builder().content(content).result(result).build();

    return ResponseEntity.of(Optional.of(diaryEmotionResponse));
  }


  @PostMapping("api/v1/test2")
  public ResponseEntity<?> test2(@RequestBody String content) {

    DiaryEmotionResponse diaryEmotionResponse = webClientDispatcher.diary2EmotionRequest(content);

    return ResponseEntity.of(Optional.of(diaryEmotionResponse));
  }

  @PostMapping("api/v1/test3")
  public ResponseEntity<?> test3(@RequestBody MusicRecommendationRequest musicRecommendationRequest) {

    MusicRecommendationWebClientResponse musicRecommendationWebClientResponse = webClientDispatcher
        .musicRecommendation(musicRecommendationRequest);

    return ResponseEntity.of(Optional.of(musicRecommendationWebClientResponse));
  }
}
