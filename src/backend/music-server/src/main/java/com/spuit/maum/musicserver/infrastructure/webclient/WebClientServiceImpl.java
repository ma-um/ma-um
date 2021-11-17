package com.spuit.maum.musicserver.infrastructure.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientServiceImpl implements WebClientService {

  private WebClient webClient;

  @Value("${server-url.auth}")
  String authUrl;

  public WebClientServiceImpl(WebClient.Builder webClientBuilder) {
    this.webClient = WebClient.builder().build();
  }

  @Override
  public String authenticateAndLoadUserId(String token) {


    return null;
  }
}
