package com.spuit.maum.diaryserver.web.response.Diary;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryWriteResponse {
  Emotion emotion;
}
