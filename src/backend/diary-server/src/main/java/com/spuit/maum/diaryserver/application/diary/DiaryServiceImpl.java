package com.spuit.maum.diaryserver.application.diary;

import com.spuit.maum.diaryserver.domain.common.BaseEntity;
import com.spuit.maum.diaryserver.domain.common.exception.ResourceNotFoundException;
import com.spuit.maum.diaryserver.domain.diary.Diary;
import com.spuit.maum.diaryserver.domain.diary.DiaryRepository;
import com.spuit.maum.diaryserver.domain.diary.Emotion;
import com.spuit.maum.diaryserver.domain.diary.Music;
import com.spuit.maum.diaryserver.infrastructure.webclient.WebClientDispatcher;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryEmotionCustomRequest;
import com.spuit.maum.diaryserver.web.request.Diary.DiaryWriteRequest;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCalenderResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryCardResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryDetailResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryTimelineResponse;
import com.spuit.maum.diaryserver.web.response.Diary.DiaryWriteResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Music 관련 Application Service의 구현체(주입되는 bean). 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

  private final WebClientDispatcher webClientDispatcher;

  @Override
  public DiaryWriteResponse write(String userId, DiaryWriteRequest diaryWriteRequest) {
    Diary diary =
        Diary.builder().title(diaryWriteRequest.getSubject()).userId(userId)
            .content(diaryWriteRequest.getContent())
            .registrationDate(diaryWriteRequest.getDate().atStartOfDay()).build();
    diaryRepository.save(diary);
    Emotion responseEmotion =
        webClientDispatcher.getEmotionByDiaryContent(diaryWriteRequest.getContent())
            .setDefaultTopEmotion().resetTopEmotionValue();
    return new DiaryWriteResponse(responseEmotion);
  }

  @Override
  public DiaryCalenderResponse findCalenderDiaryList(String userId, Integer year, Integer month) {
    LocalDate monthFirstDate = LocalDate.of(year, month, 1);
    LocalDate monthLastDate = monthFirstDate.with(TemporalAdjusters.lastDayOfMonth());
    List<Diary> diaryList =
        diaryRepository
            .findAllByUserIdAndRegistrationDateBetweenOrderByRegistrationDate(userId,
                monthFirstDate.atStartOfDay(),
                monthLastDate.atTime(23, 59, 59));

    return new DiaryCalenderResponse(diaryList);
  }

  @Override
  public void setDiaryCustomEmotion(String userId,
      DiaryEmotionCustomRequest diaryEmotionCustomRequest) {
    Diary diary =
        diaryRepository
            .findByUserIdAndRegistrationDateBetween(userId,
                diaryEmotionCustomRequest.getDate().atStartOfDay(),
                diaryEmotionCustomRequest.getDate().atTime(23, 59, 59))
            .orElseThrow(() -> new ResourceNotFoundException("date", Diary.class,
                diaryEmotionCustomRequest.getDate().toString()));

    webClientDispatcher.setEmotionByDiaryId(diary.getId(),
        diaryEmotionCustomRequest.getEmotions()
            .setTopEmotion(diaryEmotionCustomRequest.getTopEmotion()).setTopEmotionValue());
  }

  @Override
  public DiaryCardResponse findDiaryCardByUserIdAndDate(String userId, Integer year, Integer month,
      Integer date) {
    LocalDateTime first = LocalDate.of(year, month, date).atStartOfDay();
    LocalDateTime last = first.toLocalDate().atTime(23, 59, 59);

    Diary diary =
        diaryRepository.findByUserIdAndRegistrationDateBetween(userId,
            first, last).orElseThrow(() -> new ResourceNotFoundException("date", Diary.class,
            first.toLocalDate().toString()));

    return createDiaryCard(diary);
  }

  @Override
  public DiaryTimelineResponse findTimelineByUserId(String userId) {
    List<Diary> diaryList = diaryRepository.findAllByUserIdOrderByRegistrationDateDesc(userId);
    List<DiaryCardResponse> diaryCardList = new LinkedList<>();
    diaryList.forEach(diary ->
        diaryCardList.add(createDiaryCard(diary))
    );
    return new DiaryTimelineResponse(diaryCardList);
  }

  @Override
  public DiaryDetailResponse findDiaryDetailByUserIdAndDate(String userId, Integer year,
      Integer month, Integer date) {
    LocalDateTime first = LocalDate.of(year, month, date).atStartOfDay();
    LocalDateTime last = first.toLocalDate().atTime(23, 59, 59);

    Diary diary =
        diaryRepository.findByUserIdAndRegistrationDateBetween(userId,
            first, last).orElseThrow(() -> new ResourceNotFoundException("date", Diary.class,
            first.toLocalDate().toString()));

    Emotion emotion = webClientDispatcher.findEmotionByDiaryId(diary.getId()).setTopEmotionName().resetTopEmotionValue();
    List<Music> musicList = webClientDispatcher.findAllMusicByDiaryId(diary.getId());

    return DiaryDetailResponse.builder().date(first.toLocalDate()).musicList(musicList)
        .content(diary.getContent()).emotions(emotion).subject(diary.getTitle()).build();
  }

  /*

    querydsl로 리팩토링
   */
    @Override
    public List<String> getAllDiaryByUserId(String userId) {

      return diaryRepository.findAllByUserIdOrderByRegistrationDateDesc(userId).stream().map(
              BaseEntity::getId).collect(
        Collectors.toList());
  }

  private DiaryCardResponse createDiaryCard(Diary diary) {
    Emotion emotion = webClientDispatcher.findEmotionByDiaryId(diary.getId()).setTopEmotionName().resetTopEmotionValue();
    Music music = webClientDispatcher.findMusicByDiaryId(diary.getId());

    return new DiaryCardResponse(diary, music, emotion.getTopEmotion());
  }
}
