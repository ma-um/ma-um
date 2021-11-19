package com.spuit.maum.diaryserver.domain.diary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class Music {
  Long id;
  String name;
  String singer;
  String jacketUrl;
  String lyric;
}
