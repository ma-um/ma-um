package com.spuit.maum.musicserver.web.controller;

import com.spuit.maum.musicserver.infrastructure.webclient.DiaryEmotionResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TestController {

  @PostMapping("api/v1/test")
  public ResponseEntity<?> test() {
    String content = "coneteee";
    String result = "[54. 28. 36. 88. 31. 35. 34. 20. 32. 30. 36.]";
    DiaryEmotionResponse diaryEmotionResponse =
        DiaryEmotionResponse.builder().content(content).result(result).build();

    return ResponseEntity.of(Optional.of(diaryEmotionResponse));
  }
}
