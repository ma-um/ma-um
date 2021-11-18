package com.spuit.maum.musicserver.infrastructure.webclient;

import com.google.common.net.HttpHeaders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

//@Component
//@Slf4j
//public class WebClientDispatcherImpl implements WebClientDispatcher {
//
//  private final WebClient webClient;
//
//  //  @Value("${url.auth.base}")
//  private String authUrl = "http://localhost:8000/api/v1/user";
//
//  public WebClientDispatcherImpl(WebClient.Builder builder) {
//    this.webClient = builder.build();
//  }
//
//  @Override
//  public String authenticateAndGetUserId(String token) {
//    String uri = authUrl;
//    log.info("authenticate url - {}", uri);
//
//    try {
//      response = webClient
//          .post()
//          .uri(uri)
//          .accept(MediaType.APPLICATION_JSON)
//          .header(HttpHeaders.AUTHORIZATION, token)
//          .retrieve()
//          .bodyToMono(ApiResponse.class)
//          .block();
//
//      log.info("response - {}", response);
//      Map<String, String> map = (LinkedHashMap<String, String>) response.getData();
//      return map.get("userId");
//    } catch (WebClientResponseException ex) {
//      throw new UnauthorizedException(token);
//    }
//  }
//}
