package com.spuit.maum.musicserver.infrastructure.webclient;

import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import java.util.List;

/**
 * Rest 클라이언트 통신을 위한 서비스.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface WebClientDispatcher {

  String authenticateAndGetUserId(String token);

  DiaryEmotionResponse diary2EmotionRequest(String content);

  MusicRecommendationWebClientResponse musicRecommendation(
      MusicRecommendationRequest musicRecommendationRequest);

  List<String> getAllDiaryByUserId(String userId);

}
