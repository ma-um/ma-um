package com.spuit.maum.musicserver.application.music;

import com.spuit.maum.musicserver.application.ApplicationService;
import com.spuit.maum.musicserver.domain.emotion.Emotion;
import com.spuit.maum.musicserver.domain.emotion.EmotionDto;
import com.spuit.maum.musicserver.web.response.Music.GetMusicListResponse;

/**
 * Music 관련 Application Service. 비즈니스 서비스 로직을 작성한다.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface MusicService extends ApplicationService {

  GetMusicListResponse getMusicList(String diaryId);

  GetMusicListResponse setRecommendationMusicList(String diaryId, EmotionDto emotiondto);

  GetMusicListResponse getMusicListByUserId(String userId);
}
