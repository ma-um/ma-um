package com.spuit.maum.diaryserver.application.diary;

import com.spuit.maum.diaryserver.application.ApplicationService;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryEmotionCustomRequest;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCalenderResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryWriteResponse;

/**
 * Diary 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface DiaryService extends ApplicationService {
  DiaryWriteResponse write(String userId, DiaryWriteRequest diaryWriteRequest);

  DiaryCalenderResponse getCalenderDiaryList(String userId, Integer year, Integer month);

  void setDiaryCustomEmotion(String userId, DiaryEmotionCustomRequest diaryEmotionCustomRequest);
}
