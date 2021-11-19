package com.spuit.maum.diaryserver.web.response.Diary;

import com.spuit.maum.diaryserver.domain.diary.Diary;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DiaryCalenderResponse {

  @AllArgsConstructor
  @Getter
  public static class DiaryDate {

    LocalDate date;
  }

  List<DiaryDate> diaryList;

  public DiaryCalenderResponse(List<Diary> diaryList) {
    this.diaryList = new LinkedList<>();
    diaryList.forEach(diary ->
        this.diaryList.add(new DiaryDate(diary.getRegistrationDate().toLocalDate())));
  }
}
