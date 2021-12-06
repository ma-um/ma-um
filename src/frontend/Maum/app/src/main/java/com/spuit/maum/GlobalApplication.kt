package com.spuit.maum

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.kakao.sdk.common.KakaoSdk
import com.spuit.maum.service.DiaryRetrofitService
import com.spuit.maum.service.MusicRetrofitService
import com.spuit.maum.service.UserRetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: GlobalApplication

        fun ApplicationContext(): Context {
            return instance.applicationContext
        }
    }

    lateinit var userService: UserRetrofitService
    lateinit var diaryService: DiaryRetrofitService
    lateinit var musicService: MusicRetrofitService

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "bc26821d18c0095c1fef58285f615935")

        // Init Stetho
        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    /**
     * Create retrofit
     */
    fun createRetrofit() {
        // 헤더 설정(토큰 값 넣기)
        // 원래 나가려던 request 통신을 잡아다가 개조(헤더를 붙임)하고 다시 내보낸다.
        val header = Interceptor { // Interceptor : 핸드폰으로부터 통신이 나갈 때 통신을 가로챈다.
            val original = it.request() // 원래 나가려던 통신을 잡아둔다.

            // 헤더 설정 시 토큰값을 넣어주는게 목표이므로 로그인이 되었는지부터 체크
            if (checkIsLogin()) {
                // 토큰이 null이 아니라면
                getUserToken()?.let { token -> // token : getUserToken()의 리턴 값
                    val request = original.newBuilder() // 나가려던 통신을 개조한다.
                        .header("Authorization", "token " + 7777) // 헤더를 달아준다.
                        .header("Content-Type", "application/json")
                        .build()
                    it.proceed(request) // 개조된 request를 다시 내보낸다.
                }
            } else {
                it.proceed(original) // 원래 request를 내보낸다.
            }
        }

        // 클라이언트 설정
        // StethoInterceptor : 핸드폰으로 들락날락하는 모든 통신을 이 인터셉터가 낚아채서 화면에 보여준다.
        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        // retrofit 만들기
        val userRetrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8091/api/v1/user/")
//            .baseUrl("http://10.0.2.2:8080/music/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // 위에서 만든 클라이언트 넣어줌
            .build()

        val diaryRetrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8092/api/v1/diary/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // 위에서 만든 클라이언트 넣어줌
            .build()

        val musicRetrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8093/api/v1/music/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // 위에서 만든 클라이언트 넣어줌
            .build()

        // 서비스 만들기
        userService = userRetrofit.create(UserRetrofitService::class.java)
        diaryService = diaryRetrofit.create(DiaryRetrofitService::class.java)
        musicService = musicRetrofit.create(MusicRetrofitService::class.java)
    }

    /**
     * Check login
     */
    // 회원 가입을 하면 token값을 받고 그 토큰값을 SP에 저장하는데,
    /* SP에 토큰값이 없으면 로그인이 안된거고 있으면 로그인이 되었다고 판단*/ fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE) // SP 얻어오기
        val token = sp.getString("token", "null") // token 얻어오기

        if (token != "null") return true

        return false
    }

    /**
     * Get token
     */
    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE) // SP 얻어오기
        val token = sp.getString("token", "null") // token 얻어오기

        if (token == "null") return null
        return token
    }


    /**
     * drawable의 감정 이모티콘 이미지 파일 ID 가져오기
     */
    open fun getEmoticonId(emotion: String?): Int {
        when (emotion) {
            "fear" -> return R.drawable.fear
            "surprise" -> return R.drawable.surprise
            "anger" -> return R.drawable.anger
            "sadness" -> return R.drawable.sadness
            "neutrality" -> return R.drawable.neutrality
            "happiness" -> return R.drawable.happiness
            "disgust" -> return R.drawable.disgust
            "pleasure" -> return R.drawable.pleasure
            "embarrassment" -> return R.drawable.embarrassment
            "unrest" -> return R.drawable.unrest
            "bruise" -> return R.drawable.bruise
            null -> return R.drawable.neutrality
            else -> return R.drawable.neutrality
        }
    }

}
