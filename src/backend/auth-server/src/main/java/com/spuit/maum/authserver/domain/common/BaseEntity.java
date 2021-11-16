package com.spuit.maum.authserver.domain.common;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * JPA 베이스 엔티티
 *
 * superbuilder 추가
 * @author cherrtomato1
 * @version 1.0.1
 */
@ToString
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {

  @GenericGenerator(name = "HashIdGenerator", strategy = "com.spuit.maum.authserver.domain.common"
      + ".HashIdGenerator")
  @GeneratedValue(generator = "HashIdGenerator")
  @Id
  protected String id;

  @CreatedDate
  protected LocalDateTime registrationDate;
}