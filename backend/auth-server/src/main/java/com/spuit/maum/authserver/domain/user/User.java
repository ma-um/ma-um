package com.spuit.maum.authserver.domain.user;

import com.spuit.maum.authserver.domain.common.DomainModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DomainModel
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  String id;
  String oauthId;
  LocalDateTime registrationDate;
}
