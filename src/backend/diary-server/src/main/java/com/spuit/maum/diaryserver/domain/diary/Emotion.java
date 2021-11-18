package com.spuit.maum.diaryserver.domain.diary;

import java.lang.reflect.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Emotion {

  private String topEmotion;

  private Integer fear;
  private Integer surprise;
  private Integer anger;
  private Integer sadness;
  private Integer neutrality;

  private Integer happiness;
  private Integer disgust;
  private Integer pleasure;
  private Integer embarrassment;
  private Integer unrest;
  private Integer bruise;

  public Emotion setTopEmotion(String topEmotion) {
    this.topEmotion = topEmotion;
    return this;
  }

  public Emotion setTopEmotionValue() {
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        if (f.getType() != Integer.class || !f.getName().equals(this.topEmotion)) {
          continue;
        }
        int nextValue = (int) f.get(this);

        f.set(this, -nextValue);
        break;
      }
    } catch (IllegalAccessException ex) {
      throw new RuntimeException();
    }
    return this;
  }

  public Emotion setTopEmotionName() {
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        if (f.getType() != Integer.class || (Integer) f.get(this) >= 0) {
          continue;
        }
        this.topEmotion = f.getName();
        break;
      }
    } catch (IllegalAccessException ex) {
      throw new RuntimeException();
    }
    return this;
  }

  public Emotion resetTopEmotionValue() {
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        if (f.getName().equals(topEmotion)) {
          f.set(this, -(int) f.get(this));
          break;
        }
      }
    } catch (IllegalAccessException ex) {
      throw new RuntimeException();
    }
    return this;
  }

  public Emotion setDefaultTopEmotion() {
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      int maxValue = 0;
      String topEmotionName = "happiness";
      for (Field f : fields) {
        f.setAccessible(true);
        if (f.getType() != Integer.class) {
          continue;
        }

        Integer value = (Integer) f.get(this);
        if (maxValue > value) {
          continue;
        }

        maxValue = value;
        topEmotionName = f.getName();
      }
      topEmotion = topEmotionName;
      setTopEmotionValue();

    } catch (IllegalAccessException ex) {
      throw new RuntimeException();
    }
    return this;
  }
}
