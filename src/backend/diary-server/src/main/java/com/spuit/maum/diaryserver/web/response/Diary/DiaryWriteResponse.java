package com.spuit.maum.diaryserver.web.response.Diary;

import com.spuit.maum.diaryserver.domain.emotion.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryWriteResponse {
  Emotion emotion;
}
