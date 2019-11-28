package dev.bonch.kodik

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SplashActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var animation: Animation
    private lateinit var progressBar: ProgressBar
    private lateinit var layout: ConstraintLayout

    private val SPLASH_DURATION = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        layout = findViewById(R.id.splashLayout)
        progressBar = findViewById(R.id.splash_progress_bar)
        imageView = findViewById(R.id.splash_icon)
        animation = AnimationUtils.loadAnimation(baseContext, R.anim.rotate)

    }

    private fun initFunctionality() {
        layout.postDelayed({
            progressBar.visibility = View.GONE
            imageView.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationRepeat(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    intent = Intent(this@SplashActivity, RegistrationActivity::class.java)
                    startActivity(intent)
                }
            })
        }, SPLASH_DURATION.toLong())
    }

    override fun onResume() {
        super.onResume()
        initFunctionality()
    }
}
