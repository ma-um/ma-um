package com.spuit.maum.dto

import java.io.Serializable

/**
 * 일기 요약 카드에서 보여지는 음악 정보
 */
class Music(
    var name: String? = null, // 노래 제목
    var singer: String? = null, // 가수
    var jacketUrl: String? = null // 앨범 자켓 이미지 URL
): Serializable