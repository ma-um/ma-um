package com.spuit.maum.musicserver.domain.music;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * querydsl로 리팩토링
 */


@Entity
@Getter
@Builder
@NoArgsConstructor
public class DiaryMusic {
  @Id
  @GeneratedValue
  String id;

  String diaryId;

  Integer musicId;
}
