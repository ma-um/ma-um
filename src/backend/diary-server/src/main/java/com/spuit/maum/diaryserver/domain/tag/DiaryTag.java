package com.spuit.maum.diaryserver.domain.tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Diary - Tag n-n 테이블
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DiaryTag {

  @GeneratedValue
  @Id
  Long id;
  String userId;
  String tagId;
}
