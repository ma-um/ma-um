package com.spuit.maum.service

import com.spuit.maum.dto.BaseResponse
import com.spuit.maum.dto.DiaryWriteRequest
import com.spuit.maum.dto.RecommendRequest
import retrofit2.Call
import retrofit2.http.*

interface DiaryRetrofitService {

    /**
     * 해당 year, month에 작성된 일기 날짜 정보 리스트 받아오기
     */
    @GET("calender/{year}/{month}")
    fun getDiaryList(
        @Path("year") year: Int,
        @Path("month") month: Int
    ): Call<BaseResponse>

    /**
     * 해당 날짜에 해당하는 일기 요약 정보 받아오기
     */
    @GET("card/{year}/{month}/{day}")
    fun getDiaryCard(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Call<BaseResponse>

    /**
     * POST: 일기 쓰기
     */
    @POST(".")
    fun write(
        @Body diaryWriteRequest: DiaryWriteRequest
    ): Call<BaseResponse>

    /**
     * POST: 추천
     */
    @POST("emotion")
    fun recommend(
        @Body request: RecommendRequest
    ): Call<BaseResponse>

    /**
     * 타임라인
     */
    @GET("timeline")
    fun getTimeline(): Call<BaseResponse>

    /**
     * GET: 일기 상세
     */
    @GET("detail/{year}/{month}/{day}")
    fun getDiaryDetail(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Call<BaseResponse>

}
