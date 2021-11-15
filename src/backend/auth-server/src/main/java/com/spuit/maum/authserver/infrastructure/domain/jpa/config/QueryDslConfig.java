package com.spuit.maum.authserver.infrastructure.domain.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JPAQueryFactory의 빈 등록을 위한 클래스 (QueryDSL에서 의존)
 *
 * @author cherrytomato1
 * @vesrsion 1.0.0
 */
@Configuration
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}