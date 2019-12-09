package dev.bonch.kodik.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.models.User
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var registrationButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializeViews()
        clickListeners()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
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
        val name = userNameEditText.text.toString().trim()
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

                    val user = FirebaseAuth.getInstance().currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("PROFILE UPDATED", "User profile updated.")
                            }
                        }

                    saveUser(name, email, "URI", intent)
//                    finish()
                } else {
                    Toast.makeText(applicationContext, "Что-то пошло не так", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(applicationContext, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUser(name: String, email: String, uri: String, intent: Intent) {
        if (name.isNotEmpty() && email.isNotEmpty()) {
            db.collection("users")
                .document(email.toString().trim())
                .set(
                    User(
                        name,
                        email,
                        uri,
                        mutableListOf(-1),
                        mutableListOf(-1),
                        mutableListOf(false, false, false, false, false, false),
                        mutableListOf(0, 0, 0, 0, 0, 0),
                        auth.uid
                    )
                )
                .addOnSuccessListener { documentReference ->
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Error: " + e.message,
                        Toast.LENGTH_SHORT
                    ).show();
                }
        }
    }
}
