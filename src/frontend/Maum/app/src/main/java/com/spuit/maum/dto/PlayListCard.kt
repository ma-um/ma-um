package com.spuit.maum.dto

import java.io.Serializable
import java.util.*

/**
 * 전체 음악 리스트 카드
 */
class PlayListCard(
    var musicId: Int? = null,
    var name: String? = null,
    var singer: String? = null,
    var jacketUrl: String? = null
) : Serializable