package com.spuit.maum.diaryserver.web.controller;

import com.google.common.net.HttpHeaders;
import com.spuit.maum.diaryserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.diaryserver.web.response.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
public class DiaryController {

  @PostMapping
  public ApiResponse<?> write(@ApiIgnore @AuthenticationParameter @RequestHeader(name =
      HttpHeaders.AUTHORIZATION) String token,
      @ApiIgnore @RequestParam(required = false) @AuthenticationParameter String userId) {
    return ApiResponse.defaultOk(userId);
  }

}
