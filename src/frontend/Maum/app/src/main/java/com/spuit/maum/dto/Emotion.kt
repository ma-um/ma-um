package com.spuit.maum.dto

import java.io.Serializable

/**
 * 감정 수치 정보
 */
class Emotion(
    var name: String? = null,
    var koreanName: String? = null,
    var percent: Int? = null
): Serializable