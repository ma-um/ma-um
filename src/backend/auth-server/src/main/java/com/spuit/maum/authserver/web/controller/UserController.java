package com.spuit.maum.authserver.web.controller;

import com.spuit.maum.authserver.application.user.UserService;
import com.spuit.maum.authserver.domain.user.User;
import com.spuit.maum.authserver.domain.user.exception.UnauthorizedException;
import com.spuit.maum.authserver.domain.user.exception.UserNotFoundException;
import com.spuit.maum.authserver.web.aspect.AuthenticationParameter;
import com.spuit.maum.authserver.web.response.ApiResponse;
import com.spuit.maum.authserver.web.response.user.AuthenticateResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
      @RequestHeader("Authorization") @AuthenticationParameter @ApiIgnore String token,
      HttpServletResponse httpServletResponse) {

    String oauthId = token;
    User user;
    try {
      user = userService.findUserByOauthId(oauthId);
    } catch (UserNotFoundException ex) {
      user = userService.registerUserByOauthId(oauthId);
      httpServletResponse.setStatus(HttpStatus.CREATED.value());
      return ApiResponse.of(HttpStatus.CREATED, "success",
          user.getOauthId());
    }
    return ApiResponse.defaultOk(user.getOauthId());
  }

  @GetMapping("")
  public ApiResponse<?> authenticateTokenAndGetUserId(
      @RequestHeader("Authorization") @AuthenticationParameter @ApiIgnore String token) {

    User user;
    try {
      user = userService.findUserByOauthId(token);
    } catch (UserNotFoundException ex) {
      throw new UnauthorizedException(token);
    }
    return ApiResponse.defaultOk(new AuthenticateResponse(user.getId()));
  }
}
