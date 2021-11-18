package com.spuit.maum.musicserver.application.emotion;

import com.spuit.maum.musicserver.domain.common.ResourceNotFoundException;
import com.spuit.maum.musicserver.domain.emotion.Emotion;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.domain.emotion.EmotionRepository;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.web.response.emotion.AnalysisEmotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Emotion 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {

  private final EmotionRepository emotionRepository;

  private final WebClientDispatcher webClientDispatcher;

  @Override
  public AnalysisEmotionResponse analysisEmotionByContent(String content) {
//    Emotion emotion =
//        emotionRepository.findById(diaryId).orElseThrow(() -> new ResourceNotFoundException(
//            "diaryId", Emotion.class, diaryId));
    return new AnalysisEmotionResponse(EmotionDto.of(webClientDispatcher.diary2EmotionRequest(content).resultStringToList()));
  }
}
