package com.spuit.maum.diaryserver.web.response.Diary;

import com.spuit.maum.diaryserver.domain.diary.Diary;
import com.spuit.maum.diaryserver.domain.diary.Music;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DiaryCardResponse {

  LocalDate date;
  String topEmotion;
  String subject;
  String content;
  Music topMusic;

  public DiaryCardResponse(Diary diary, Music topMusic, String topEmotion) {
    this.date = diary.getRegistrationDate().toLocalDate();
    this.subject = diary.getTitle();
    this.content = diary.getContent();
    this.topEmotion = topEmotion;
    this.topMusic = topMusic;
  }
}
