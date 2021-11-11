package com.spuit.maum.musicserver.domain.music;

import com.spuit.maum.musicserver.domain.common.DomainModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Music 애그리거트 루트 도메인.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@DomainModel
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Music {

  @Id
  @GeneratedValue
  String id;
}
