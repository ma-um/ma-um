package com.spuit.maum.dto

import java.io.Serializable

/**
 * 일기 요약 카드
 */
class DiaryCard(
    var date: String? = null, // 날짜 ex) 2021-11-17
    var topEmotion: String? = null, // 감정(영문) ex) anger
    var subject: String? = null, // 제목
    var content: String? = null, // 내용
    var topMusic: Music? = null // 음악(제목, 가수, 앨범 URL)
): Serializable