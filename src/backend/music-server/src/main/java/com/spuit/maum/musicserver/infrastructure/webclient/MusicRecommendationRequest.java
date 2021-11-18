package com.spuit.maum.musicserver.infrastructure.webclient;

import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicRecommendationRequest {
  EmotionDto emotions;
  List<Long> existedMusicIdList;
}
