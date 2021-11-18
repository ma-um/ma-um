package com.spuit.maum.musicserver.infrastructure.webclient;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WebClientServiceImpl implements WebClientService {

  private final WebClient webClient;

//  @Value("${url.auth.base}")
  private String authUrl = "http://localhost:8091/api/v1/user";;

  public WebClientServiceImpl(WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  @Override
  public String authenticateAndLoadUserId(String token) {
    String uri = authUrl + "/load-user";
    log.info("authenticate url - {}", uri);

    return webClient
        .get()
        .uri(uri)
//        .accept(MediaType.APPLICATION_JSON)
//        .header(HttpHeaders.AUTHORIZATION, token)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
