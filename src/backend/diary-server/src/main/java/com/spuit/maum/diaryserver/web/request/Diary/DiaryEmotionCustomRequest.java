package com.spuit.maum.diaryserver.web.request.Diary;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class DiaryEmotionCustomRequest {
  @DateTimeFormat(pattern="yyyy-MM-d")
  LocalDate date;
  String topEmotion;
  Emotion emotions;
}
