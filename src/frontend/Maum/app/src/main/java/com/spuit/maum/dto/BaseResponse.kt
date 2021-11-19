package com.spuit.maum.dto

import java.io.Serializable

class BaseResponse(
    var data: Any? = null, // data
    var message: String? = null, // success
    var statusCode: String? = null, // OK
    var statusCodeValue: Int? = null // 200
) : Serializable