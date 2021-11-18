package com.spuit.maum.diaryserver.infrastructure.webclient;

import com.spuit.maum.diaryserver.domain.emotion.Emotion;

/**
 * Rest 클라이언트 통신을 위한 서비스.
 *
 * @author cherrytomato1
 * @version 1.0.0
 *
 */
public interface WebClientDispatcher {

  String authenticateAndGetUserId(String token);
  Emotion getEmotionByDiaryContent(String content);
}
