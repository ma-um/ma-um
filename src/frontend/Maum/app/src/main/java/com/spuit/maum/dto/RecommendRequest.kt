package com.spuit.maum.dto

import java.io.Serializable
import java.time.LocalDate

class RecommendRequest(
    var date: String? = null, // yyyy-mm-dd
    var emotions: Emotions? = null,
    var topEmotion: String? = null
) : Serializable