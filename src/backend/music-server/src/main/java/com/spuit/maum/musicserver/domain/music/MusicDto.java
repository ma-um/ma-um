package com.spuit.maum.musicserver.domain.music;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicDto {

  Long id;
  String name;
  String singer;
  String jacketUrl;
  String lyric;

  public static MusicDto of(Music music) {
    return new MusicDto(music.getId(), music.getName(), music.getSinger(), music.getJacketUrl(),
        music.lyric);
  }
}
