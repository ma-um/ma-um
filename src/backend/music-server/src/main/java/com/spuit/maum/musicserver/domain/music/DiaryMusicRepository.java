package com.spuit.maum.musicserver.domain.music;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/*

  querdsl로 리팩토링필요
 */
public interface DiaryMusicRepository extends JpaRepository<DiaryMusic, Long> {
  List<DiaryMusic> findAllByDiaryId(String diaryId);
  List<DiaryMusic> findAllByDiaryIdInOrderByRegistrationDate(List<String> diaryId);
}
