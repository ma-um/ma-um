package com.spuit.maum.authserver.web.controller;

import com.spuit.maum.authserver.application.user.UserService;
import com.spuit.maum.authserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.authserver.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * User 관련 api Rest controller.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/load-user")

  public ApiResponse<?> validateUserOrRegister(
      @RequestHeader("Authorization") @AuthenticationParameter @ApiIgnore String token) {


    return ApiResponse.defaultOk(token);
  }

  @GetMapping("/")
  public ApiResponse<?> authenticateTokenAndGetUserId(
      @RequestHeader("Authorization") @AuthenticationParameter @ApiIgnore String token) {
    return null;
  }

}
