package com.spuit.maum.musicserver.domain.music;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * querydsl로 리팩토링
 */


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryMusic {
  @Id
  @GeneratedValue
  Long id;

  String diaryId;

  Long musicId;
}
