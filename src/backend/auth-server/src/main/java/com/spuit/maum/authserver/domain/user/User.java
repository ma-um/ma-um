package com.spuit.maum.authserver.domain.user;

import com.spuit.maum.authserver.domain.common.BaseEntity;
import com.spuit.maum.authserver.domain.common.DomainModel;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * User 애그리거트 루트 도메인.
 *
 * equals 재정의
 * @author cherrytomato1
 * @version 1.0.2
 */

@DomainModel
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class User extends BaseEntity {
  @Column(unique = true)
  String oauthId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    User user = (User) o;
    if (this.id != null && user.getId() != null) {
      return this.id.equals(user.getId());
    }
    return this.oauthId.equals(user.getOauthId());
  }
}
