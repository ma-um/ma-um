package com.spuit.maum.musicserver.web.response.emotion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DiaryEmotionResponse {

  String content;
  String result;

  public List<Integer> resultStringToList() {

    return Arrays.stream(
        result.trim().substring(1, result.length() - 1)
            .replaceAll("\\.", "").split(" "))
        .map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
  }

}
