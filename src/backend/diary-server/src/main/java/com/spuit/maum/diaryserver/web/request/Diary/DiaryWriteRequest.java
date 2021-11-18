package com.spuit.maum.diaryserver.web.request.Diary;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
public class DiaryWriteRequest {
  @DateTimeFormat(pattern="yyyy-MM-d")
  LocalDate date;
  String subject;
  String content;
}
