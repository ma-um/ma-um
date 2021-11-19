package com.spuit.maum.musicserver.domain.music;

import com.spuit.maum.musicserver.domain.common.DomainModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Music 애그리거트 루트 도메인.
 *
 * emotion 연관관계 테이블로 리팩토링 필요
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
public class Music {

  @Id
  @GeneratedValue
  Long id;
  String name;
  String singer;
  String jacketUrl;
  @Column(columnDefinition = "TEXT")
  String lyric;

  String fear;
  String surprise;
  String anger;
  String sadness;
  String neutrality;
  String happiness;
  String disgust;
  String pleasure;
  String embarrassment;
  String unrest;
  String bruise;
}
