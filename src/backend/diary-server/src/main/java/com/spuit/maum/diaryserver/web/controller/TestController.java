package com.spuit.maum.diaryserver.web.controller;

import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.diaryserver.web.controller.MusicRecommendationResponse.MusicId;
import java.util.ArrayList;
import java.util.LinkedList;
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

  @PostMapping("api/v1/recommendation/music_recommendation")
  public ResponseEntity<?> test2(
      @RequestBody MusicRecommendationRequest musicRecommendationRequest) {
    MusicRecommendationResponse musicRecommendationResponse =
        new MusicRecommendationResponse(new LinkedList<>());
    musicRecommendationResponse.getMusicIdList().add(new MusicId(231));
    musicRecommendationResponse.getMusicIdList().add(new MusicId(444));
    musicRecommendationResponse.getMusicIdList().add(new MusicId(121));
    return ResponseEntity.of(Optional.of(musicRecommendationResponse));

  }
}
