package com.spuit.maum.dto

import java.io.Serializable
import java.util.*

/**
 * 11가지 감정 %(0~100) 리스트 + topEmotion
 */
class DetailEmotions(
    var topEmotion: String? = null,
    var fear: Int? = null,
    var surprise: Int? = null,
    var anger: Int? = null,
    var sadness: Int? = null,
    var neutrality: Int? = null,
    var happiness: Int? = null,
    var disgust: Int? = null,
    var pleasure: Int? = null,
    var embarrassment: Int? = null,
    var unrest: Int? = null,
    var bruise: Int? = null
) : Serializable