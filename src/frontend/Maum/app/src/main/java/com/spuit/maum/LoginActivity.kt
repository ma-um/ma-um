package com.spuit.maum

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.spuit.maum.dto.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = this.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("token", "" + 7777)
        editor.commit()

        // 자동로그인 - 화면 그리기 전에 로그인 체크하고 로그인 되어 있으면 캘린더 액티비티로 넘어감
        if ((application as GlobalApplication).checkIsLogin()) {
            finish()
            startActivity(
                Intent(this@LoginActivity, BottomNavigationActivity::class.java)
            )
        } else {
            setContentView(R.layout.activity_login)

            initView(this)
            setupListener(this)
        }
    }

    fun setupListener(activity: Activity) {
        loginBtn.setOnClickListener {
            login(this@LoginActivity)
        }
    }

    /**
     * 로그인
     */
    fun login(activity: Activity) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("log__", "" + token)
                Log.d("log__", "" + error)
                Toast.makeText(activity, "로그인 실패: " + error, Toast.LENGTH_LONG).show()
            } else if (token != null) {
                // 토큰 정보 보기
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Toast.makeText(activity, "토큰 받아오기 실패: " + error, Toast.LENGTH_LONG).show()
                    } else if (tokenInfo != null) {
                        register(this@LoginActivity, tokenInfo.id)
                    }
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    /**
     * 로그인하면 카카오 서버로부터 받은 회원번호를 SP에 저장
     */
    fun register(activity: Activity, id: Long) {
//        val request = LoginRequest(id = id)
//        (application as GlobalApplication).service.register(request)
//            .enqueue(object : Callback<LoginRequest> {
//                override fun onResponse(
//                    call: Call<LoginRequest>,
//                    response: Response<LoginRequest>
//                ) {
//                    Log.d("%login", "" + response.body())
//                    if (response.isSuccessful) {
//                        Log.d("%login", response.body().toString())
//                        saveUserToken(id, activity) // token을 SP에 저장
//                        (application as GlobalApplication).createRetrofit() // 헤더 다시 설정
//
//                        // 메인화면으로 이동
//                        activity.finish()
//                        activity.startActivity(
//                            Intent(activity, BottomNavigationActivity::class.java)
//                        )
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginRequest>, t: Throwable) {
//                    Toast.makeText(activity, "가입에 실패하였습니다." + call.toString(), Toast.LENGTH_LONG)
//                        .show()
//                }
//            })

        saveUserToken(id, activity) // token을 SP에 저장
        (application as GlobalApplication).createRetrofit() // 헤더 다시 설정

        // 회원 테이블에 insert
        (application as GlobalApplication).userService.register()
            .enqueue(object: Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("log_", "회원가입 성공!")
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                }
            })

        // 메인화면으로 이동
        activity.finish()
        activity.startActivity(
            Intent(activity, BottomNavigationActivity::class.java)
        )
    }

    // 회원가입 시 받은 토큰을 SP에 저장하는 함수
    fun saveUserToken(token: Long, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("token", "" + token)
        editor.commit()
    }

    fun initView(activity: Activity) {
        loginBtn = activity.findViewById(R.id.login_btn)
    }

}
