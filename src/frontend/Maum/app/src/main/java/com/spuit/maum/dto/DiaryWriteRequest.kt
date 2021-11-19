package com.spuit.maum.dto

import java.io.Serializable
import java.time.LocalDate

class DiaryWriteRequest(
    var date: String? = null, // yyyy-mm-dd
    var subject: String? = null,
    var content: String? = null
) : Serializable