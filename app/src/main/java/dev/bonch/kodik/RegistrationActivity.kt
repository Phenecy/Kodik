package dev.bonch.kodik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var registrationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializeViews()
        clickListeners()


    }

    private fun initializeViews() {
        userNameEditText = registration_name_edit_text
        emailEditText = registration_email_edit_text
        passwordEditText = registration_password_edit_text
        passwordRepeatEditText = registration_password_repeat_edit_text
        registrationButton = registration_button
    }

    private fun clickListeners(){
        registrationButton.setOnClickListener {
            TODO("Добавить переход на главную активность")
        }
    }
}
