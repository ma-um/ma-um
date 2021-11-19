package com.spuit.maum.service

import com.spuit.maum.dto.Diary
import com.spuit.maum.dto.LoginRequest
import retrofit2.Call
import retrofit2.http.*

// baseURL 뒤에 /temp/test 같은 url들을 관리하는 파일
interface MusicRetrofitService {

    /**
     * 회원 가입
     */
    @POST("temp/signup")
    fun register(
        @Body param: LoginRequest
    ): Call<LoginRequest>

    /**
     * 사용자가 작성한 모든 일기 리스트를 받는다.
     */
    @GET("temp/diarylist")
    fun getAllDiary(): Call<ArrayList<Diary>>

}
