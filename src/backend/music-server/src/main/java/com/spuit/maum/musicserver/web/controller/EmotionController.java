package com.spuit.maum.musicserver.web.controller;

import com.spuit.maum.musicserver.application.emotion.EmotionService;
import com.spuit.maum.musicserver.web.request.emotion.SetCustomEmotionRequest;
import com.spuit.maum.musicserver.web.response.ApiResponse;
import com.spuit.maum.musicserver.web.response.emotion.AnalysisEmotionResponse;
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

  @GetMapping("/analysis/{diaryId}")
  public ApiResponse<?> analysisEmotion(@PathVariable String diaryId) {

    AnalysisEmotionResponse analysisEmotionResponse = emotionService.analysisEmotionByContent(diaryId);
    return null;
  }

  @GetMapping("/{diaryId}")
  public ApiResponse<?> loadEmotion(@PathVariable String diaryId) {

    return null;
  }

  @PostMapping("/{diaryId}")
  public ApiResponse<?> setCustomEmotion(@PathVariable String diaryId, @RequestBody
      SetCustomEmotionRequest setCustomEmotionRequest) {

    return null;
  }

}
