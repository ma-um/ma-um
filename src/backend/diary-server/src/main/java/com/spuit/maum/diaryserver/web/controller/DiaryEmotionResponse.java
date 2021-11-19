package com.spuit.maum.diaryserver.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiaryEmotionResponse {

  String content;
  String result;

  public List<Integer> resultStringToList() {

    return Arrays.stream(
        result.trim().substring(1, result.length() - 1)
            .replaceAll("\\.", "").split(" "))
        .map(Integer::parseInt).collect(Collectors.toList());
  }

}
