package dev.bonch.kodik.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.SplashActivity

private lateinit var profileExitButton: Button
private lateinit var profileChangeButton: Button
private lateinit var profileNameEditText: EditText
private lateinit var profileEmailEditText: EditText
private lateinit var profilePasswordEditText: EditText

class ProfileDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_data, container, false)
        profileExitButton = view.findViewById(R.id.profile_exit_button)
        profileNameEditText = view.findViewById(R.id.profile_name_edit_text)
        profileEmailEditText = view.findViewById(R.id.profile_email_edit_text)
        profilePasswordEditText = view.findViewById(R.id.profile_password_edit_text)
        profileChangeButton = view.findViewById(R.id.profile_change_button)
        val user = FirebaseAuth.getInstance().currentUser
//        setEnableEditText(true)

        profileExitButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }

        profileChangeButton.setOnClickListener {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(profileNameEditText.text.toString().trim())
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/lis.bmp?alt=media&token=fd1a4615-382f-4857-81d8-00671dec3fd8"))
                .build()

            if (profileEmailEditText.text.toString().isNotEmpty()) {
                user?.updateEmail(profileEmailEditText.text.toString().trim())
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("EMAIL CHANGED", "User email address updated.")
                        }
                    }
            }
            if (profilePasswordEditText.text.toString().isNotEmpty()) {
                user?.updatePassword(profilePasswordEditText.toString().trim())
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("PASSWORD CHANGED", "User password updated.")
                        }
                    }
            }
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("PROFILE UPDATED", "User profile updated.")
                    }
                }
            ProfileFragment().setUserData()
        }
        return view
    }

//    private fun setEnableEditText(bool:Boolean){
//        profileNameEditText.setEnabled(bool)
//        profileEmailEditText.setEnabled(bool)
//        profilePasswordEditText.setEnabled(bool)
//    }


}