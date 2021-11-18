package com.spuit.maum.diaryserver.domain.emotion;

import java.lang.reflect.Field;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Emotion {

  private String topEmotion;

  private final Integer fear;
  private final Integer surprise;
  private final Integer anger;
  private final Integer sadness;
  private final Integer neutrality;

  private final Integer happiness;
  private final Integer disgust;
  private final Integer pleasure;
  private final Integer embarrassment;
  private final Integer unrest;
  private final Integer bruise;

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
