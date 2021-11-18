package com.spuit.maum.musicserver.web.controller;

import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


  @GetMapping("/music/{diaryId}")
  public ApiResponse<?> getMusicList(@PathVariable String diaryId) {

    return null;
  }

  @GetMapping("/music/recommendation/{diaryId}")
  public ApiResponse<?> setRecommendationMusicList(@PathVariable String diaryId) {

  }
}
