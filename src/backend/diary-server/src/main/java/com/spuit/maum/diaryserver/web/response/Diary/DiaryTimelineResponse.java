package com.spuit.maum.diaryserver.web.response.Diary;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryTimelineResponse {
  List<DiaryCardResponse> diaryCardList;

}
