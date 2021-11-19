package com.spuit.maum.dto

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class CalendarListResponse(
    var diaryList: ArrayList<CalendarResponse>? = null
) : Serializable