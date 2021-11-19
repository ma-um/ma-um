package com.spuit.maum.dto

import java.io.Serializable

/**
 * 일기 요약 카드
 */
class DiaryCardListResponse(
    var diaryCardList: ArrayList<DiaryCard>? = null
) : Serializable