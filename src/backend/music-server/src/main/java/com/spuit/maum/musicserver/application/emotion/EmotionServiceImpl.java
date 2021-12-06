package com.spuit.maum.musicserver.application.emotion;

import com.spuit.maum.musicserver.domain.common.ResourceNotFoundException;
import com.spuit.maum.musicserver.domain.emotion.Emotion;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.domain.emotion.EmotionRepository;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import com.spuit.maum.musicserver.web.response.emotion.GetEmotionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Emotion 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {

  private final EmotionRepository emotionRepository;

  private final WebClientDispatcher webClientDispatcher;

  @Override
  public GetEmotionResponse analysisEmotionByContent(String content) {

    DiaryEmotionResponse response = webClientDispatcher.diary2EmotionRequest(content);
    log.info("response - {}", response);
    return new GetEmotionResponse(
            EmotionDto.of(response.resultStringToList()));
  }

  @Override
  public GetEmotionResponse findEmotionByDiaryId(String diaryId) {
    Emotion emotion =
        emotionRepository.findById(diaryId).orElseThrow(() -> new ResourceNotFoundException(
            "diaryId", Emotion.class, diaryId));
    return new GetEmotionResponse(EmotionDto.of(emotion));
  }

  @Override
  public void updateOrSaveEmotionByDiaryId(String diaryId,
      SetCustomEmotionRequest setCustomEmotionRequest) {
    Emotion emotion =
        emotionRepository.findById(diaryId).orElseGet(() -> new Emotion(diaryId));
    emotion.updateEmotion(setCustomEmotionRequest.getEmotion());
    emotionRepository.save(emotion);
  }
}
