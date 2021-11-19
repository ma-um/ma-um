package com.spuit.maum.dto

import java.io.Serializable
import java.util.*

class Diary(
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null,
    var hashtags: String? = null,
    var date: Date? = null
): Serializable