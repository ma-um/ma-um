package com.spuit.maum.musicserver.web.response.Music;

import com.spuit.maum.musicserver.domain.music.MusicDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMusicListResponse {
  List<MusicDto> musicList;
}
