package com.spuit.maum.musicserver.infrastructure.webclient;

import com.spuit.maum.musicserver.web.request.MusicRecommendationRequest;
import com.spuit.maum.musicserver.web.response.DiaryEmotionResponse;
import com.spuit.maum.musicserver.web.response.Music.MusicRecommendationResponse;

/**
 * Rest 클라이언트 통신을 위한 서비스.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface WebClientDispatcher {

  DiaryEmotionResponse diary2EmotionRequest(String content);

  MusicRecommendationResponse musicRecommendation(MusicRecommendationRequest musicRecommendationRequest);

}
