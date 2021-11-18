package com.spuit.maum.musicserver.application.emotion;

import com.spuit.maum.musicserver.domain.common.ResourceNotFoundException;
import com.spuit.maum.musicserver.domain.emotion.Emotion;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.domain.emotion.EmotionRepository;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.emotion.GetEmotionResponse;
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
  public GetEmotionResponse analysisEmotionByContent(String content) {

    return new GetEmotionResponse(
        EmotionDto.of(webClientDispatcher.diary2EmotionRequest(content).resultStringToList()));
  }

  @Override
  public GetEmotionResponse findEmotionByDiaryId(String diaryId) {
    Emotion emotion =
        emotionRepository.findById(diaryId).orElseThrow(() -> new ResourceNotFoundException(
            "diaryId", Emotion.class, diaryId));
    return new GetEmotionResponse(EmotionDto.of(emotion));
  }

  @Override
  public void updateEmotionByDiaryId(String diaryId,
      SetCustomEmotionRequest setCustomEmotionRequest) {
    Emotion emotion =
        emotionRepository.findById(diaryId).orElseThrow(() -> new ResourceNotFoundException(
            "diaryId", Emotion.class, diaryId));
    emotion.updateEmotion(setCustomEmotionRequest.getEmotion());
    emotionRepository.save(emotion);
  }
}
