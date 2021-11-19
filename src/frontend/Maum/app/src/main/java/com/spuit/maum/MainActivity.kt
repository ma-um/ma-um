package com.spuit.maum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Splash Activity
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashAnimation()
    }

    /**
     * Splash animation
     */
    fun splashAnimation() {
        val logoImgAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_logo)
        splash_app_name.startAnimation(logoImgAnim)

        logoImgAnim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                // Animation 끝나면 로그인 액티비티로
                finish()
                startActivity(
                    Intent(this@MainActivity, LoginActivity::class.java)
                )
            }

            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

}
