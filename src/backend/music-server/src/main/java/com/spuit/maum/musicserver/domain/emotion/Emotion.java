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
  /*

    Id는 diary, music의 id를 참조
   */
  @Id
  String id;
  Integer type;

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

  public Emotion(String id) {
    this.id = id;
  }

  public void updateEmotion(EmotionDto emotionDto) {
    this.fear = emotionDto.fear;
    this.surprise = emotionDto.surprise;
    this.anger = emotionDto.anger;
    this.sadness = emotionDto.sadness;
    this.neutrality = emotionDto.neutrality;
    this.happiness = emotionDto.happiness;
    this.disgust = emotionDto.disgust;
    this.pleasure = emotionDto.pleasure;
    this.embarrassment = emotionDto.embarrassment;
    this.unrest = emotionDto.unrest;
    this.bruise = emotionDto.bruise;
  }
}
