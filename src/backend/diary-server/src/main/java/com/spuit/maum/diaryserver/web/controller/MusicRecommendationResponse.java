package com.spuit.maum.diaryserver.web.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicRecommendationResponse {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MusicId {
    Integer id;
  }

  List<MusicId> musicIdList;
}
