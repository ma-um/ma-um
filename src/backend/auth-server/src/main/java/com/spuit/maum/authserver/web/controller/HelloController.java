package com.spuit.maum.authserver.web.controller;

import com.spuit.maum.authserver.web.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hello")
public class HelloController {

  @AllArgsConstructor
  @Getter
  private static class HelloResponse {
    String hello;
    Long value;
  }


  @GetMapping("/")
  public ApiResponse<?> hello() {
    return ApiResponse.of(HttpStatus.OK, "success", new HelloResponse("hello", 1L));
  }
}
