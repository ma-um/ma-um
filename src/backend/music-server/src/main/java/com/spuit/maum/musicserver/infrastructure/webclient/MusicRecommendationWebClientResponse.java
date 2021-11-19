package com.spuit.maum.musicserver.infrastructure.webclient;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicRecommendationWebClientResponse {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MusicId {
    Long id;
  }

  List<MusicId> musicIdList;
}
