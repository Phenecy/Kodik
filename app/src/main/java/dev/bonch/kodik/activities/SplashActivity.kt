package dev.bonch.kodik.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import kotlinx.android.synthetic.main.activity_splash_afterload.*
import java.net.URL

class SplashActivity : AppCompatActivity() {

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
            val regButton: Button = splash_reg_button
            val loginButton: Button = splash_login_button
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
