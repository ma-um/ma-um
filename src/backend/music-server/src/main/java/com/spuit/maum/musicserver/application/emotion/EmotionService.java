package com.spuit.maum.musicserver.application.emotion;

import com.spuit.maum.musicserver.application.ApplicationService;
import com.spuit.maum.musicserver.web.response.emotion.AnalysisEmotionResponse;

/**
 * Emotion 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface EmotionService extends ApplicationService {
  AnalysisEmotionResponse analysisEmotionByContent(String diaryId);
}
