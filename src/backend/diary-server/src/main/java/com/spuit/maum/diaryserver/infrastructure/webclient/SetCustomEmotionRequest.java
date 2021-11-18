package com.spuit.maum.diaryserver.infrastructure.webclient;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SetCustomEmotionRequest {
  Emotion emotion;
}
