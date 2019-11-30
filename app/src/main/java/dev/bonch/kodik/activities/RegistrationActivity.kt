package dev.bonch.kodik.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dev.bonch.kodik.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var registrationButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializeViews()
        clickListeners()

        auth = FirebaseAuth.getInstance()

    }

    private fun initializeViews() {
        userNameEditText = registration_name_edit_text
        emailEditText = login_email_edit_text
        passwordEditText = login_password_edit_text
        passwordRepeatEditText = registration_password_repeat_edit_text
        registrationButton = login_button
        alreadyHaveAccountTextView = registration_already_have_acc
    }

    private fun clickListeners() {
        registrationButton.setOnClickListener {
            onClickRegister()
        }
        alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClickRegister() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val passwordRepeat = passwordRepeatEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            return
        }
        if (password == passwordRepeat) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Что-то пошло не так", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(applicationContext, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
        }
    }
}
