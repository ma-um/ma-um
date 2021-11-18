package com.spuit.maum.diaryserver.web.controller;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.application.diary.DiaryService;
import com.spuit.maum.diaryserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.diaryserver.web.request.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.ApiResponse;
import com.spuit.maum.diaryserver.web.response.DiaryWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Diary 관련 api Rest controller.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/diary")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping
  public ApiResponse<?> write(@ApiIgnore @AuthenticationParameter @RequestHeader(name =
      HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId,
      @RequestBody DiaryWriteRequest diaryWriteRequest) {

    DiaryWriteResponse diaryWriteResponse = diaryService.write(userId, diaryWriteRequest);

    return ApiResponse.defaultOk(diaryWriteResponse);
  }

}
