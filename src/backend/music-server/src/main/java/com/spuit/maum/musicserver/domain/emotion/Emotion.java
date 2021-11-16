package com.spuit.maum.musicserver.domain.emotion;

import com.spuit.maum.musicserver.domain.common.DomainModel;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Emotion 애그리거트 루트 도메인.
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
public class Emotion {
  @Id
  String id;
  Integer type;

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
