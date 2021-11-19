package com.spuit.maum.musicserver.infrastructure.webclient;

import static org.junit.jupiter.api.Assertions.*;

import com.spuit.maum.musicserver.web.response.emotion.DiaryEmotionResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiaryEmotionResponseTest {

  @Test
  @DisplayName("다이어리 리스폰스 테스트")
  public void diaryResponseTest() {
    //given
    String result = "[54. 28. 36. 88. 31. 35. 34. 20. 32. 30. 36.]";
    List<Integer> list = Arrays.asList(54, 28, 36, 88, 31, 35, 34, 20, 32, 30, 36);

    //when
    DiaryEmotionResponse diaryEmotionResponse =
        DiaryEmotionResponse.builder().result(result).content("test").build();

    //then
    assertEquals(list, diaryEmotionResponse.resultStringToList());
  }
}