package com.spuit.maum.diaryserver.web.controller;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.application.diary.DiaryService;
import com.spuit.maum.diaryserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryEmotionCustomRequest;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.ApiResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCalenderResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCardResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryDetailResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryTimelineResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    return ApiResponse.of(HttpStatus.CREATED, "success", diaryWriteResponse);
  }

  @GetMapping("/calender/{year}/{month}")
  public ApiResponse<?> getCalenderDiaryList(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId,
      @PathVariable Integer year, @PathVariable Integer month) {

    DiaryCalenderResponse diaryCalenderResponse = diaryService.findCalenderDiaryList(userId, year,
        month);
    return ApiResponse.defaultOk(diaryCalenderResponse);
  }

  @PostMapping("/emotion")
  public ApiResponse<?> setCustomEmotion(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId,
      @RequestBody DiaryEmotionCustomRequest diaryEmotionCustomRequest) {

    diaryService.setDiaryCustomEmotion(userId, diaryEmotionCustomRequest);
    return ApiResponse.defaultOk(null);
  }

  @GetMapping("/card/{year}/{month}/{day}")
  public ApiResponse<?> getDiaryCard(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId,
      @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day) {

    DiaryCardResponse diaryCardResponse = diaryService.findDiaryCardByUserIdAndDate(userId, year,
        month, day);
    return ApiResponse.defaultOk(diaryCardResponse);
  }

  @GetMapping("/timeline")
  public ApiResponse<?> getTimelineList(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId
  ) {
    DiaryTimelineResponse diaryTimelineResponse = diaryService.findTimelineByUserId(userId);

    return ApiResponse.defaultOk(diaryTimelineResponse);
  }

  @GetMapping("/detail/{year}/{month}/{day}")
  public ApiResponse<?> diaryDetails(
      @ApiIgnore @AuthenticationParameter @RequestHeader(name =
          HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId,
      @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day) {

    DiaryDetailResponse diaryDetailResponse = diaryService.findDiaryDetailByUserIdAndDate(userId,
        year, month, day);
    return ApiResponse.defaultOk(diaryDetailResponse);
  }

  @GetMapping("/{userId}")
  public ApiResponse<?> getAllDiaryByUserId(@PathVariable String userId) {

    return ApiResponse.defaultOk(diaryService.getAllDiaryByUserId(userId));
  }
}
