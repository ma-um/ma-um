package com.spuit.maum.musicserver.web.request;

import com.spuit.maum.musicserver.domain.emotion.Emotion;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicRecommendationRequest {
  Emotion emotions;
  List<Integer> existedMusicIdList;
}
