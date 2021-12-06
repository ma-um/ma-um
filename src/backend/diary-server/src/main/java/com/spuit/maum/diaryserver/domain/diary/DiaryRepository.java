package com.spuit.maum.diaryserver.domain.diary;

import com.spuit.maum.diaryserver.domain.diary.Diary;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Diary의 데이터를 관리하는 레포지토리. 도메인 애그리거트에만 의존.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface DiaryRepository extends JpaRepository<Diary, String> {

  List<Diary> findAllByUserIdAndRegistrationDateBetweenOrderByRegistrationDate(String userId,
      LocalDateTime first,
      LocalDateTime last);

  Optional<Diary> findByUserIdAndRegistrationDateBetween(String userId, LocalDateTime first,
      LocalDateTime last);

  List<Diary> findAllByUserIdOrderByRegistrationDateDesc(String userId);
}
