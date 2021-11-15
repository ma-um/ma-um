package com.spuit.maum.authserver.domain.user;

import com.spuit.maum.authserver.domain.common.BaseEntity;
import com.spuit.maum.authserver.domain.common.DomainModel;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User 애그리거트 루트 도메인.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@DomainModel
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User extends BaseEntity {
  String oauthId;
}