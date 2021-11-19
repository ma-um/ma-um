package com.spuit.maum.diaryserver.infrastructure.webclient;


import com.spuit.maum.diaryserver.domain.diary.Music;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMusicListResponse {
  List<Music> musicList;
}
