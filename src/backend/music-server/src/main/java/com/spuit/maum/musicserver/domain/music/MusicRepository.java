package com.spuit.maum.musicserver.domain.music;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Music 의 데이터를 관리하는 레포지토리. 도메인 애그리거트에만 의존.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface MusicRepository extends JpaRepository<Music, Long> {
  List<Music> findDistinctByIdIn(List<Long> id);
}
