package dev.bonch.kodik.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import dev.bonch.kodik.R
import kotlinx.android.synthetic.main.activity_splash_afterload.*

class SplashActivity : AppCompatActivity() {

    private lateinit var splashImageView: ImageView
    private lateinit var imageView: ImageView
    private lateinit var animation: Animation
    private lateinit var progressBar: ProgressBar
    private lateinit var layout: ConstraintLayout

    private lateinit var auth: FirebaseAuth

    private val SPLASH_DURATION = 500


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
                    auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser
                    updateUI(currentUser)
                }
            })
        }, SPLASH_DURATION.toLong())
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_splash_afterload)
            splashImageView = findViewById(R.id.splash_image_view)
            val regButton: Button = splash_reg_button
            val loginButton: Button = splash_login_button

            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/body.png?alt=media&token=57309c14-ca30-4549-9a23-dbc9299a6aaa")
                .into(splashImageView)

            regButton.setOnClickListener {
                intent = Intent(this@SplashActivity, RegistrationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                finish()
            }
            loginButton.setOnClickListener {
                intent = Intent(this@SplashActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                finish()
            }
            return
        }
    }

    override fun onResume() {
        super.onResume()
        initFunctionality()
    }
}
