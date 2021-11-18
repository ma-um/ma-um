package com.spuit.maum.diaryserver.domain.emotion;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Emotion {
  Integer fear;
  Integer surprise;
  Integer anger;
  Integer sadness;
  Integer neutrality;

  Integer happiness;
  Integer disgust;
  Integer pleasure;
  Integer embarrassment;
  Integer unrest;
  Integer bruise;
}
