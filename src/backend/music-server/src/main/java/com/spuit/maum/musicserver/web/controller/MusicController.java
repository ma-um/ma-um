package com.spuit.maum.musicserver.web.controller;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.musicserver.application.emotion.EmotionService;
import com.spuit.maum.musicserver.application.music.MusicService;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.musicserver.web.response.ApiResponse;
import com.spuit.maum.musicserver.web.response.Music.GetMusicListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Music 관련 api Rest controller.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/music")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MusicController {

  private final MusicService musicService;

  private final EmotionService emotionService;

  @GetMapping("/{diaryId}")
  public ApiResponse<?> getMusicListByDiaryId(@PathVariable String diaryId) {
    GetMusicListResponse musicListResponse =
        musicService.getMusicList(diaryId);
    return ApiResponse.defaultOk(musicListResponse);
  }

  @GetMapping("/recommendation/{diaryId}")
  public ApiResponse<?> setRecommendationMusicList(@PathVariable String diaryId) {

    EmotionDto emotionDto = emotionService.findEmotionByDiaryId(diaryId).getEmotion();

    GetMusicListResponse musicListResponse =
        musicService.setRecommendationMusicList(diaryId, emotionDto);
    return ApiResponse.defaultOk(musicListResponse);
  }

  @GetMapping("/list")
  public ApiResponse<?> getMusicListByToken(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId) {

    GetMusicListResponse getMusicListResponse = musicService.getMusicListByUserId(userId);
    return ApiResponse.defaultOk(getMusicListResponse);
  }
}
