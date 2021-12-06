package com.spuit.maum.musicserver.domain.emotion;

import java.lang.reflect.Field;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class EmotionDto {

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

  public static EmotionDto of(Emotion emotion) {
    return EmotionDto.builder().bruise(emotion.bruise).anger(emotion.anger).disgust(emotion.disgust)
        .embarrassment(emotion.embarrassment).fear(emotion.fear).happiness(emotion.happiness).neutrality(emotion.neutrality)
        .sadness(emotion.sadness).pleasure(emotion.pleasure).surprise(emotion.surprise).unrest(emotion.unrest).build();
  }

  public static EmotionDto of(List<Integer> emotionValueList) {
    return new EmotionDto(emotionValueList.get(0), emotionValueList.get(1),
        emotionValueList.get(2), emotionValueList.get(3),
        emotionValueList.get(4), emotionValueList.get(5), emotionValueList.get(6),
        emotionValueList.get(7), emotionValueList.get(8), emotionValueList.get(9),
        emotionValueList.get(10));
  }
  public EmotionDto resetTopEmotionValue() {
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        if (f.getType() != Integer.class || (Integer) f.get(this) >= 0) {
          continue;
        }
        f.set(this, -(int) f.get(this));
      }
    } catch (IllegalAccessException ex) {
      throw new RuntimeException();
    }
    return this;
  }

  public Emotion toEmotion(String id) {
    return Emotion.builder().bruise(bruise).anger(anger).disgust(disgust)
        .embarrassment(embarrassment).fear(fear).happiness(happiness).neutrality(neutrality)
        .sadness(sadness).pleasure(pleasure).surprise(surprise).unrest(unrest).id(id).build();
  }
}
