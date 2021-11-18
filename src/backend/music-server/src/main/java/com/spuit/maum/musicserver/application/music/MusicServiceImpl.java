package com.spuit.maum.musicserver.application.music;

import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.domain.music.DiaryMusic;
import com.spuit.maum.musicserver.domain.music.DiaryMusicRepository;
import com.spuit.maum.musicserver.domain.music.MusicRepository;
import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationRequest;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.web.response.Music.GetMusicListResponse;
import io.swagger.models.auth.In;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Music 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

  private final MusicRepository musicRepository;

  private final DiaryMusicRepository diaryMusicRepository;

  private final WebClientDispatcher webClientDispatcher;

  @Override
  public GetMusicListResponse setRecommendationMusicList(String diaryid, EmotionDto emotionDto) {

    List<Integer> existingMusicIdList = new ArrayList<>();
    diaryMusicRepository.findAllByDiaryId(diaryid)
        .forEach(diaryMusic -> existingMusicIdList.add(diaryMusic.getMusicId()));

    webClientDispatcher.musicRecommendation(new MusicRecommendationRequest(emotionDto, existingMusicIdList))
    return null;
  }
}
