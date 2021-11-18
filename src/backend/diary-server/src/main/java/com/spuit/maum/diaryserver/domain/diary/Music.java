package com.spuit.maum.diaryserver.domain.diary;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Music {
  private final String name;
  private final String singer;
  private final String jacketUrl;
}
