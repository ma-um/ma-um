package com.spuit.maum.musicserver.web.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 토큰의 유효성을 검사하는 aspect에 필요한 어노테이션
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AuthenticationParameter {
}
