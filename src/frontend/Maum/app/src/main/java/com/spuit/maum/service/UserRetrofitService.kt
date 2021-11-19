package com.spuit.maum.service

import com.spuit.maum.dto.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitService {

    @GET("load-user")
    fun register(): Call<BaseResponse>

}
