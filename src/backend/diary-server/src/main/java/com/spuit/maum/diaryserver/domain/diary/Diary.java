package com.spuit.maum.diaryserver.domain.diary;

import com.spuit.maum.diaryserver.domain.common.BaseEntity;
import com.spuit.maum.diaryserver.domain.common.DomainModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Diary 애그리거트 루트 도메인.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@DomainModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Diary extends BaseEntity {

  @Column(nullable = false)
  String userId;
  String title;
  String content;

}
