package edu.sliit.cargameapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var slogan: TextView
    private lateinit var logo: ImageView
    private lateinit var topView1: View
    private lateinit var topView2: View
    private lateinit var topView3: View
    private lateinit var bottomView1: View
    private lateinit var bottomView2: View
    private lateinit var bottomView3: View
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making the app fullscreen
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        setContentView(R.layout.activity_splash)

        // Initializing views
        name = findViewById(R.id.name)
        slogan = findViewById(R.id.sologan)
        logo = findViewById(R.id.logo)
        topView1 = findViewById(R.id.topView1)
        topView2 = findViewById(R.id.topView2)
        topView3 = findViewById(R.id.topView3)
        bottomView1 = findViewById(R.id.bottomView1)
        bottomView2 = findViewById(R.id.bottomView2)
        bottomView3 = findViewById(R.id.bottomView3)

        // Animations
        val logoAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        val nameAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        val topViewAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animations)
        val bottomViewAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_views_animations)
        val topView2Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animations)
        val bottomView2Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_views_animations)
        val topView3Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animations)
        val bottomView3Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_views_animations)

        topView1.startAnimation(topViewAnimation)
        bottomView1.startAnimation(bottomViewAnimation)

        // Animation listeners
        topViewAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                topView2.visibility = View.VISIBLE
                bottomView2.visibility = View.VISIBLE

                topView2.startAnimation(topView2Animation)
                bottomView2.startAnimation(bottomView2Animation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        bottomViewAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                topView3.visibility = View.VISIBLE
                bottomView3.visibility = View.VISIBLE

                topView3.startAnimation(topView3Animation)
                bottomView3.startAnimation(bottomView3Animation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        bottomView3Animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                logo.visibility = View.VISIBLE
                logo.startAnimation(logoAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        logoAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                name.visibility = View.VISIBLE
                name.startAnimation(nameAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        nameAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                slogan.visibility = View.VISIBLE
                val animateText = slogan.text.toString()
                slogan.text = ""
                count = 0
                val timer = object : CountDownTimer(animateText.length * 100L, 100L) {
                    override fun onTick(millisUntilFinished: Long) {
                        count++
                        val animatedText = animateText.substring(0, count)
                        slogan.text = animatedText
                    }

                    override fun onFinish() {
                        // Start PlayActivity after animations finish
                        startActivity(Intent(this@SplashActivity, playActivity::class.java))
                        finish() // Finish SplashActivity to prevent it from showing again on back press
                    }
                }
                timer.start()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

    }
}
