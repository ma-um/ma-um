package com.spuit.maum.diaryserver.web.response.Diary;

import com.spuit.maum.diaryserver.domain.diary.Emotion;
import com.spuit.maum.diaryserver.domain.diary.Music;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiaryDetailResponse {

  LocalDate date;
  Emotion emotions;
  String subject;
  String content;
  List<Music> musicList;

}