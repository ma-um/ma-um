package com.spuit.maum.diaryserver.application.diary;

import com.spuit.maum.diaryserver.domain.diary.Diary;
import com.spuit.maum.diaryserver.domain.diary.DiaryRepository;
import com.spuit.maum.diaryserver.domain.emotion.Emotion;
import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.diaryserver.web.request.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.DiaryWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Music 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

  private final WebClientDispatcher webClientDispatcher;

  @Override
  public DiaryWriteResponse write(String userId, DiaryWriteRequest diaryWriteRequest) {
    Diary diary =
        Diary.builder().title(diaryWriteRequest.getSubject()).userId(userId)
            .content(diaryWriteRequest.getContent()).build();
    diaryRepository.save(diary);
    Emotion responseEmotion =
        webClientDispatcher.getEmotionByDiaryContent(diaryWriteRequest.getContent());
    return new DiaryWriteResponse(responseEmotion);
  }
}
