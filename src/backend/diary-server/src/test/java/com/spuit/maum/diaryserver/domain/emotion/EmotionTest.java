package com.spuit.maum.diaryserver.domain.emotion;

import static org.junit.jupiter.api.Assertions.*;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmotionTest {

  @Test
  @DisplayName("최고 감정의 값을 음수로 바꾼다.")
  public void setTopEmotionValueTest() {
    //given
    String topEmotion = "disgust";
    Emotion emotion =
        Emotion.builder().fear(3).anger(5).disgust(30).bruise(20).embarrassment(22).happiness(55)
            .neutrality(33).pleasure(50).sadness(32).surprise(12).unrest(55).topEmotion(topEmotion).build();

    //when
    emotion = emotion.setTopEmotionValue();

    //then
    assertTrue(emotion.getDisgust() < 0);
  }

  @Test
  @DisplayName("음수 감정을 최고 감정으로 설정한다.")
  public void setTopEmotionNameTest() {
    //given
    String topEmotion = "disgust";
    Emotion emotion =
        Emotion.builder().fear(3).anger(5).disgust(-30).bruise(20).embarrassment(22).happiness(55)
            .neutrality(33).pleasure(50).sadness(32).surprise(12).unrest(55).build();
    //when
    emotion = emotion.setTopEmotionName();

    //then
    assertEquals(topEmotion, emotion.getTopEmotion());
  }

  @Test
  @DisplayName("가장 높은 값을 기본 최고 감정으로 설정한다.")
  public void setDefaultTopEmotionTest() {
    //given
    String topEmotion = "unrest";
    Emotion emotion =
        Emotion.builder().fear(3).anger(5).disgust(30).bruise(20).embarrassment(22).happiness(55)
            .neutrality(33).pleasure(50).sadness(32).surprise(12).unrest(55).build();
    //when
    emotion = emotion.setDefaultTopEmotion();

    //then
    assertEquals(topEmotion, emotion.getTopEmotion());
  }
}