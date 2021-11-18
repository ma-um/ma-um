package com.spuit.maum.musicserver.web.response.emotion;

import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetEmotionResponse {
  EmotionDto emotion;
}
