package com.spuit.maum.musicserver.domain.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * domain 엔티티임을 표시하기 위한 어노테이션
 *
 * @author cherrytomato1
 * @version 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainModel {

}