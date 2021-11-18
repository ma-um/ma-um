package com.spuit.maum.musicserver.application.emotion;

import com.spuit.maum.musicserver.application.ApplicationService;
import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.emotion.GetEmotionResponse;

/**
 * Emotion 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface EmotionService extends ApplicationService {

  GetEmotionResponse analysisEmotionByContent(String content);

  GetEmotionResponse findEmotionByDiaryId(String diaryId);

  void updateOrSaveEmotionByDiaryId(String diaryId, SetCustomEmotionRequest setCustomEmotionRequest);
}
