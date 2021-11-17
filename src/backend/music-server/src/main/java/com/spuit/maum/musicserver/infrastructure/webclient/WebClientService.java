package com.spuit.maum.musicserver.infrastructure.webclient;

/**
 * Rest 클라이언트 통신을 위한 서비스.
 *
 * @author cherrytomato1
 * @version 1.0.0
 *
 */
public interface WebClientService {

  String authenticateAndLoadUserId(String token);
}
