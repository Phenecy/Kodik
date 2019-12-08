package dev.bonch.kodik.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.models.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 100

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var googleSignInButton: SignInButton
    private lateinit var registrationNeededText: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        initializeViews()
        clickListeners()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        } else return
    }

    private fun initializeViews() {
        emailEditText = login_email_edit_text
        passwordEditText = login_password_edit_text
        loginButton = login_button
        googleSignInButton = login_google_sign_in
        registrationNeededText = login_registration
        googleSignInButton.setSize(SignInButton.SIZE_ICON_ONLY)
    }

    private fun clickListeners() {
        loginButton.setOnClickListener {
            onClickLogin()
        }
        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
        registrationNeededText.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_RECEIVER_FOREGROUND
            startActivity(intent)
        }
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("GSI", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("GoogleAuth", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Credential", "signInWithCredential:success")
                    val user = auth.currentUser

                    db.collection("users")
                        .add(
                            User(
                                user?.email.toString().trim(),
                                user?.email.toString().trim(),
                                "uri",
                                mutableListOf(-1),
                                mutableListOf(-1),
                                mutableListOf(false, false, false, false, false, false)
                            )
                        )
                        .addOnSuccessListener { documentReference ->
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@LoginActivity,
                                "Error: " + e.message,
                                Toast.LENGTH_SHORT
                            ).show();
                        }

                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Credential", "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication Failed.", Toast.LENGTH_SHORT)
                        .show()
                    updateUI(null)
                }
            }
    }

    private fun onClickLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            return
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Почта или пароль не совпадают",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
