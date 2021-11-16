package com.spuit.maum.authserver.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user의 데이터를 관리하는 레포지토리. 도메인 애그리거트에만 의존.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByOauthId(String oauthId);
}
