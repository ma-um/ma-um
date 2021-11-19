package com.spuit.maum.diaryserver.application.diary;

import com.spuit.maum.diaryserver.application.ApplicationService;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryEmotionCustomRequest;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCalenderResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCardResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryDetailResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryTimelineResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryWriteResponse;
import java.util.List;

/**
 * Diary 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface DiaryService extends ApplicationService {
  DiaryWriteResponse write(String userId, DiaryWriteRequest diaryWriteRequest);

  DiaryCalenderResponse findCalenderDiaryList(String userId, Integer year, Integer month);

  void setDiaryCustomEmotion(String userId, DiaryEmotionCustomRequest diaryEmotionCustomRequest);

  DiaryCardResponse findDiaryCardByUserIdAndDate(String userId, Integer year, Integer month,
      Integer date);

  DiaryTimelineResponse findTimelineByUserId(String userId);

  DiaryDetailResponse findDiaryDetailByUserIdAndDate(String userId, Integer year, Integer month,
      Integer date);

  List<String> getAllDiaryByUserId(String userId);
}
