package com.spuit.maum.dto

import java.io.Serializable

/**
 * 일기 상세 정보 Response
 */
class DiaryDetailResponse(
    var date: String? = null,
    var emotions: DetailEmotions? = null,
    var subject: String? = null,
    var content: String? = null,
    var musicList: ArrayList<Music>? = null
) : Serializable