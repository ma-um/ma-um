package com.spuit.maum.musicserver.domain.emotion;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Emotion 의 데이터를 관리하는 레포지토리. 도메인 애그리거트에만 의존.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */
public interface EmotionRepository extends JpaRepository<Emotion, String> {

}