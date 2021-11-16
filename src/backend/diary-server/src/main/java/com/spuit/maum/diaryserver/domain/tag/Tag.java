package com.spuit.maum.diaryserver.domain.tag;

import com.spuit.maum.diaryserver.domain.common.DomainModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Tag 테이블
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@DomainModel
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Tag {

  @GeneratedValue
  @Id
  Long id;
  String tagName;
}
