package com.spuit.maum.dto

import java.io.Serializable
import java.time.LocalDate

class DiaryDateResponse(
    var date: LocalDate? = null // yyyy-mm-dd
): Serializable