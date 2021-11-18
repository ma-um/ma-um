package com.spuit.maum.diaryserver.web.controller;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicRecommendationRequest {
  Emotion emotions;
  List<Integer> existedMusicIdList;
}
