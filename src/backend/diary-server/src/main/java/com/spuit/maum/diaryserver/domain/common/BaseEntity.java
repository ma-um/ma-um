package com.spuit.maum.diaryserver.domain.common;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
 * @author cherrtomato1
 * @version 1.0
 */
@ToString
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity {

  @GenericGenerator(name = "HashIdGenerator", strategy = "com.spuit.maum.diaryserver.domain.common"
      + ".HashIdGenerator")
  @GeneratedValue(generator = "HashIdGenerator")
  @Id
  protected String id;

  protected LocalDateTime registrationDate;
}