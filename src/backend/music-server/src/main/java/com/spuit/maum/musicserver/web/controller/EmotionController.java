package com.spuit.maum.musicserver.web.controller;

import com.spuit.maum.musicserver.application.emotion.EmotionService;
import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.ApiResponse;
import com.spuit.maum.musicserver.web.response.emotion.GetEmotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/emotion")
public class EmotionController {

  private final EmotionService emotionService;

  @PostMapping("/analysis")
  public ApiResponse<?> analysisEmotion(@RequestBody String content) {

    GetEmotionResponse getEmotionResponse = emotionService
        .analysisEmotionByContent(content);
    return ApiResponse.defaultOk(getEmotionResponse);
  }

  @GetMapping("/{diaryId}")
  public ApiResponse<?> loadEmotion(@PathVariable String diaryId) {

    GetEmotionResponse getEmotionResponse = emotionService.findEmotionByDiaryId(diaryId);
    return ApiResponse.defaultOk(getEmotionResponse);
  }

  @PostMapping("/{diaryId}")
  public ApiResponse<?> setCustomEmotion(@PathVariable String diaryId, @RequestBody
      SetCustomEmotionRequest setCustomEmotionRequest) {

    emotionService.updateOrSaveEmotionByDiaryId(diaryId, setCustomEmotionRequest);

    return ApiResponse.defaultOk(null);
  }

}
