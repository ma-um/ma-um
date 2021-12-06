package com.spuit.maum.musicserver.application.music;

import com.spuit.maum.musicserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.domain.music.DiaryMusic;
import com.spuit.maum.musicserver.domain.music.DiaryMusicRepository;
import com.spuit.maum.musicserver.domain.music.Music;
import com.spuit.maum.musicserver.domain.music.MusicDto;
import com.spuit.maum.musicserver.domain.music.MusicRepository;
import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationRequest;
import com.spuit.maum.musicserver.infrastructure.webclient.MusicRecommendationWebClientResponse.MusicId;
import com.spuit.maum.musicserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.musicserver.web.response.Music.GetMusicListResponse;
import io.swagger.models.auth.In;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  public GetMusicListResponse getMusicList(String diaryId) {
    List<MusicDto> musicDtoList =
        diaryMusicRepository.findAllByDiaryId(diaryId).stream()
            .map(diaryMusic -> MusicDto.of(musicRepository.getById(diaryMusic.getMusicId())))
            .collect(Collectors.toList());
    return new GetMusicListResponse(musicDtoList);
  }

  @Override
  public GetMusicListResponse setRecommendationMusicList(String diaryId, EmotionDto emotionDto) {

    List<Long> existingMusicIdList = new ArrayList<>();
    diaryMusicRepository.findAllByDiaryId(diaryId)
        .forEach(diaryMusic -> existingMusicIdList.add(diaryMusic.getMusicId()));

    List<MusicId> musicIdList =
        webClientDispatcher.musicRecommendation(new MusicRecommendationRequest(emotionDto.resetTopEmotionValue(),
            existingMusicIdList)).getMusicIdList();

    List<MusicDto> musicDtoList = new ArrayList<>();

    musicIdList.forEach(musicId -> {
          musicDtoList.add(MusicDto.of(musicRepository.findById(musicId.getId()).orElseThrow(
              () -> new ResourceNotFoundException("musicId", Music.class,
                  musicId.getId().toString()))));
          diaryMusicRepository
              .save(DiaryMusic.builder().diaryId(diaryId).musicId(musicId.getId()).build());
        }
    );

    return new GetMusicListResponse(musicDtoList);
  }

  @Override
  public GetMusicListResponse getMusicListByUserId(String userId) {
    List<String> diaryIdList = webClientDispatcher.getAllDiaryByUserId(userId);
    List<Long> musicIdList =
        diaryMusicRepository.findAllByDiaryIdInOrderByRegistrationDate(diaryIdList).stream()
            .map(DiaryMusic::getMusicId).distinct().collect(
            Collectors.toList());
    List<MusicDto> musicDtoList =
        musicRepository.findDistinctByIdIn(musicIdList).stream().map(MusicDto::of)
            .collect(
                Collectors.toList());
    return new GetMusicListResponse(musicDtoList);
  }
}
