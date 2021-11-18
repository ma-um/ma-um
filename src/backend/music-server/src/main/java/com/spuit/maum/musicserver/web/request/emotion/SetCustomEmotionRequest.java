package com.spuit.maum.musicserver.web.request.emotion;

import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import lombok.Getter;

@Getter
public class SetCustomEmotionRequest {
  EmotionDto emotion;
}
