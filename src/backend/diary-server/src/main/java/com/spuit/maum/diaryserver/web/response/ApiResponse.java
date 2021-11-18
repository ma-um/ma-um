package com.spuit.maum.diaryserver.web.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

/**
 * 모든 response를 담는 클래스
 * <p>
 * defaultOk() 추가
 *
 * @param <T> 데이터 타입
 * @author cherrytomato1
 * @version 1.0.1
 */

@AllArgsConstructor
@Getter
@ToString
public class ApiResponse<T> {

  private final HttpStatus statusCode;
  private final int statusCodeValue;
  private final String message;
  private final T data;

  /**
   * @param httpStatus 응답 코드
   * @param message    응답 메시지
   * @param data       데이터
   * @param <T>        데이터 타입
   * @return ApiResponse 인스턴스
   */
  public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
    return new ApiResponse<>(httpStatus, httpStatus.value(), message, data);
  }

  public static <T> ApiResponse<T> defaultOk(T data) {
    return new ApiResponse<>(HttpStatus.OK, 200, "success", data);
  }
}